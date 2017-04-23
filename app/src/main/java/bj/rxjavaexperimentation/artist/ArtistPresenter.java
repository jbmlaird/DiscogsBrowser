package bj.rxjavaexperimentation.artist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.artist.epoxy.DetailedAdapter;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@Singleton
public class ArtistPresenter implements ArtistContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private ArtistContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private DetailedAdapter detailedAdapter;
    private CompositeDisposable compositeDisposable;
    private LogWrapper log;
    private ArtistController artistController;
    private Function<ArtistResult, ArtistResult> removeUnwantedLinksFunction;

    @Inject
    public ArtistPresenter(@NonNull Context context, @NonNull ArtistContract.View view, @NonNull SearchDiscogsInteractor searchDiscogsInteractor,
                           @NonNull MySchedulerProvider mySchedulerProvider, @NonNull DetailedAdapter detailedAdapter, @NonNull CompositeDisposable compositeDisposable,
                           @NonNull LogWrapper log, @NonNull ArtistController artistController, @NonNull Function<ArtistResult, ArtistResult> removeUnwantedLinksFunction)
    {
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.detailedAdapter = detailedAdapter;
        this.compositeDisposable = compositeDisposable;
        this.log = log;
        this.artistController = artistController;
        this.removeUnwantedLinksFunction = removeUnwantedLinksFunction;
    }

    @Override
    public void getData(String id)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchArtistDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(removeUnwantedLinksFunction)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(artist ->
                {
                    artistController.setArtist(artist);
                    log.e(TAG, artist.getProfile());
                }, error ->
                {
                    log.e(TAG, "onFetchArtistDetailsError");
                    error.printStackTrace();
                }));
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
                    log.e(TAG, label.getName());
                }, error ->
                {
                    log.e(TAG, "onFetchLabelDetails");
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
                    log.e(TAG, master.getTitle());
                }, error ->
                {
                    log.e(TAG, "onFetchMasterDetailsError");
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
                            log.e(TAG, release.getTitle());
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
                            log.e(TAG, "onReleaseDetailsError");
                            error.printStackTrace();
                        }
                ));
    }

    @Override
    public void setupRecyclerView(Context context, RecyclerView rvDetailed, String title)
    {
        rvDetailed.setLayoutManager(new LinearLayoutManager(context));
        rvDetailed.setAdapter(artistController.getAdapter());
        artistController.setHeader(title);
        artistController.requestModelBuild();
    }

    // TODO: Reimplement
//    @Override
//    public void displayLabelReleases(String labelId, String releasesUrl)
//    {
//        view.displayLabelReleases(labelId, releasesUrl);
//    }

    @Override
    public void unsubscribe()
    {
        compositeDisposable.dispose();
    }
}
