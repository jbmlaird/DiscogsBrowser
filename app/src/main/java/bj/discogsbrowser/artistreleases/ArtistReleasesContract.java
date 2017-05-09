package bj.discogsbrowser.artistreleases;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import bj.discogsbrowser.common.BasePresenter;
import bj.discogsbrowser.common.SingleListView;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Josh Laird on 10/04/2017.
 */

public interface ArtistReleasesContract
{
    interface View extends SingleListView
    {
        Observable<CharSequence> filterIntent();
    }

    interface Presenter extends BasePresenter
    {
        void getArtistReleases(String id);

        void setupRecyclerView(RecyclerView recyclerView, FragmentActivity activity);

        void connectToBehaviorRelay(String searchFilter);

        void setupFilter();
    }
}
