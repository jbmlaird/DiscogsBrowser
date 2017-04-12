package bj.rxjavaexperimentation.detailedview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.detailedview.epoxy.DetailedAdapter;
import bj.rxjavaexperimentation.discogs.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 07/04/2017.
 */

@Singleton
public class DetailedPresenter implements DetailedContract.Presenter
{
    private static final String TAG = "DetailedPresenter";
    private Context context;
    private DetailedContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private DetailedBodyModelPresenter detailedBodyModelPresenter;
    private MySchedulerProvider mySchedulerProvider;
    private RecyclerView rvDetailed;
    private DetailedAdapter detailedAdapter;

    @Inject
    public DetailedPresenter(Context context, DetailedContract.View view, SearchDiscogsInteractor searchDiscogsInteractor, DetailedBodyModelPresenter detailedBodyModelPresenter, MySchedulerProvider mySchedulerProvider)
    {
        this.context = context;
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.detailedBodyModelPresenter = detailedBodyModelPresenter;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    @Override
    public void fetchDetailedInformation(String type, String id)
    {
        switch (type)
        {
            case "release":
                searchDiscogsInteractor.fetchReleaseDetails(id)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(release ->
                        {
                            detailedAdapter.addRelease(release);
                            Log.e(TAG, release.getTitle());
                        });
                break;
            case "artist":
                searchDiscogsInteractor.fetchArtistDetails(id)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(artist ->
                        {
                            detailedAdapter.addArtist(artist);
                            Log.e(TAG, artist.getProfile());
                        });
                break;
            case "master":
                searchDiscogsInteractor.fetchMasterDetails(id)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(master ->
                        {
                            detailedAdapter.addMaster(master);
                            Log.e(TAG, master.getTitle());
                        });
                break;
            case "label":
                searchDiscogsInteractor.fetchLabelDetails(id)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .doOnComplete(() ->
                                searchDiscogsInteractor.fetchLabelReleases(id)
                                        .subscribeOn(mySchedulerProvider.io())
                                        .observeOn(mySchedulerProvider.ui())
                                        .subscribe(labelReleases ->
                                                detailedAdapter.addLabelReleases(labelReleases)))
                        .subscribe(label ->
                        {
                            detailedAdapter.addLabel(label);
                            Log.e(TAG, label.getName());
                        });
                break;
        }
    }

    @Override
    public void setupRecyclerView(RecyclerView rvDetailed, String title)
    {
        this.rvDetailed = rvDetailed;
        rvDetailed.setLayoutManager(new LinearLayoutManager(context));
        detailedAdapter = new DetailedAdapter(this, context, title, detailedBodyModelPresenter);
        rvDetailed.setAdapter(detailedAdapter);
    }

    @Override
    public void displayRelease(Integer id, String title)
    {
        view.displayRelease(id, title);
    }

    @Override
    public void displayLabelReleases(Integer labelId, String releasesUrl)
    {
        // TODO: Need a new list activity here?
        view.displayLabelReleases(labelId, releasesUrl);
    }

    // Use an extra request here?
//    public void fetchArtistReleases(String artistId)
//    {
//        searchDiscogsInteractor.getArtistsReleases(artistId)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(releases -> detailedAdapter.addArtistBody(releases),
//                        error -> Log.e(TAG, "error"));
//    }
}
