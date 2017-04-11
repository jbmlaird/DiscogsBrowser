package bj.rxjavaexperimentation.detailedview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.detailedview.epoxy.DetailedAdapter;
import bj.rxjavaexperimentation.discogs.SearchDiscogsInteractor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private RecyclerView rvDetailed;
    private DetailedAdapter detailedAdapter;

    @Inject
    public DetailedPresenter(Context context, DetailedContract.View view, SearchDiscogsInteractor searchDiscogsInteractor, DetailedBodyModelPresenter detailedBodyModelPresenter)
    {
        this.context = context;
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.detailedBodyModelPresenter = detailedBodyModelPresenter;
    }

    @Override
    public void fetchDetailedInformation(String type, String id)
    {
        switch (type)
        {
            case "release":
                searchDiscogsInteractor.fetchReleaseDetails(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(release ->
                        {
                            detailedAdapter.addRelease(release);
                            Log.e(TAG, release.getTitle());
                        });
                break;
            case "artist":
                searchDiscogsInteractor.fetchArtistDetails(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(artist ->
                        {
                            detailedAdapter.addArtist(artist);
                            Log.e(TAG, artist.getProfile());
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

    // Use an extra request here?
//    public void fetchArtistReleases(String artistId)
//    {
//        searchDiscogsInteractor.getArtistsReleases(artistId)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(releases -> detailedAdapter.addArtistBody(releases),
//                        error -> Log.e(TAG, "error"));
//    }
}
