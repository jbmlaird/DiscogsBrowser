package bj.rxjavaexperimentation.detailedview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.detailedview.epoxy.DetailedAdapter;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;
import bj.rxjavaexperimentation.utils.WantedUrl;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@Singleton
public class DetailedPresenter implements DetailedContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private DetailedContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private DetailedAdapter detailedAdapter;
    private ArtistsBeautifier artistsBeautifier;
    private CompositeDisposable compositeDisposable;
    private LogWrapper logWrapper;
    private DetailedController detailedController;

    @Inject
    public DetailedPresenter(@NonNull Context context, @NonNull DetailedContract.View view, @NonNull SearchDiscogsInteractor searchDiscogsInteractor,
                             @NonNull MySchedulerProvider mySchedulerProvider,
                             @NonNull ArtistsBeautifier artistsBeautifier, @NonNull DetailedAdapter detailedAdapter, @NonNull CompositeDisposable compositeDisposable,
                             @NonNull LogWrapper logWrapper, @NonNull DetailedController detailedController)
    {
        this.context = context;
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistsBeautifier = artistsBeautifier;
        this.detailedAdapter = detailedAdapter;
        this.compositeDisposable = compositeDisposable;
        this.logWrapper = logWrapper;
        this.detailedController = detailedController;
    }

    @Override
    public void fetchDetailedInformation(String type, String id)
    {
        switch (type)
        {
            case "release":
                fetchReleaseInformation(id);
                break;
            case "artist":
                fetchArtistDetails(id);
                break;
            case "master":
                fetchMasterDetails(id);
                break;
            case "label":
                fetchLabelDetails(id);
                break;
        }
    }

    private void fetchLabelDetails(String id)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchLabelDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .doOnComplete(() ->
                        searchDiscogsInteractor.fetchLabelReleases(id)
                                .subscribeOn(mySchedulerProvider.io())
                                .observeOn(mySchedulerProvider.ui())
                                .subscribe(labelReleases ->
                                                detailedAdapter.addLabelReleases(labelReleases)
                                        , Throwable::printStackTrace))
                .subscribe(label ->
                {
                    detailedAdapter.addLabel(label);
                    logWrapper.e(TAG, label.getName());
                }, error ->
                {
                    logWrapper.e(TAG, "onFetchLabelDetails");
                    error.printStackTrace();
                }));
    }

    private void fetchMasterDetails(String id)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchMasterDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(master ->
                {
                    detailedAdapter.addMaster(master);
                    logWrapper.e(TAG, master.getTitle());
                }, error ->
                {
                    logWrapper.e(TAG, "onFetchMasterDetailsError");
                    error.printStackTrace();
                }));
    }

    private void fetchArtistDetails(String id)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchArtistDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(removeUnwantedLinks())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(artist ->
                {
                    detailedController.setArtist(artist);
//                    detailedAdapter.addArtist(artist);
                    logWrapper.e(TAG, artist.getProfile());
                }, error ->
                {
                    logWrapper.e(TAG, "onFetchArtistDetailsError");
                    error.printStackTrace();
                }));
    }

    private void fetchReleaseInformation(String id)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchReleaseDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(release ->
                        {
                            detailedAdapter.addRelease(release);
                            logWrapper.e(TAG, release.getTitle());
                            searchDiscogsInteractor.getReleaseMarketListings(id, "release")
                                    .subscribeOn(mySchedulerProvider.io())
                                    .observeOn(mySchedulerProvider.ui())
                                    .subscribe(o ->
                                                    detailedAdapter.setReleaseListings(o),
                                            error ->
                                                    detailedAdapter.setReleaseListingsError()
                                    );
                        }, error ->
                        {
                            logWrapper.e(TAG, "onReleaseDetailsError");
                            error.printStackTrace();
                        }
                ));
    }

    @Override
    public void setupRecyclerView(RecyclerView rvDetailed, String title, Toolbar toolbar)
    {
        rvDetailed.setLayoutManager(new LinearLayoutManager(context));
//        detailedAdapter.setHeader(title);
//        rvDetailed.setAdapter(detailedAdapter);
        rvDetailed.setAdapter(detailedController.getAdapter());
        detailedController.setHeader(title);
        detailedController.requestModelBuild();
        // TODO: Change actionbar on scroll
    }

    @Override
    public void displayLabelReleases(String labelId, String releasesUrl)
    {
        // TODO: Need a new list activity here?
        view.displayLabelReleases(labelId, releasesUrl);
    }

    @Override
    public void unsubscribe()
    {
        compositeDisposable.dispose();
    }

    /**
     * Only return a list of wanted URLs with no duplicate websites.
     *
     * @return ArtistResult with the unwanted URLs removed.
     */
    private Function<ArtistResult, ArtistResult> removeUnwantedLinks()
    {
        return artistResult ->
        {
            if (artistResult.getUrls() == null)
                return artistResult;
            boolean wikipedia, youtube, facebook, spotify, twitter, soundcloud;
            wikipedia = youtube = facebook = spotify = twitter = soundcloud = false;
            ArrayList<WantedUrl> wantedUrls = new ArrayList<>();
            for (String string : artistResult.getUrls())
            {
                if (string.contains("spotify") && !spotify)
                {
                    wantedUrls.add(new WantedUrl(string, "Listen on Spotify", "#6ae368", "{fa-spotify}"));
                    spotify = true;
                }
                else if (string.contains("wikipedia") && !wikipedia)
                {
                    wantedUrls.add(new WantedUrl(string, "Learn more on Wikipedia", "#000000", "{fa-wikipedia-w}"));
                    wikipedia = true;
                }
                else if (string.contains("facebook") && !facebook)
                {
                    wantedUrls.add(new WantedUrl(string, "Check out Facebook", "#3b5998", "{fa-facebook}"));
                    facebook = true;
                }
                else if (string.contains("twitter") && !twitter)
                {
                    wantedUrls.add(new WantedUrl(string, "Follow on Twitter", "#4099FF", "{fa-twitter}"));
                    twitter = true;
                }
                else if (string.contains("youtube") && !youtube)
                {
                    wantedUrls.add(new WantedUrl(string, "Watch on YouTube", "#bb0000", "{fa-youtube-play}"));
                    youtube = true;
                }
                else if (string.contains("soundcloud") && !soundcloud)
                {
                    wantedUrls.add(new WantedUrl(string, "Listen on SoundCloud", "#ff7700", "{fa-soundcloud}"));
                    soundcloud = true;
                }
            }
            artistResult.setWantedUrls(wantedUrls);
            return artistResult;
        };
    }
}
