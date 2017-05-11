package bj.discogsbrowser.artistreleases.fragments;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import bj.discogsbrowser.common.BasePresenter;

/**
 * Created by Josh Laird on 11/05/2017.
 */
public interface ArtistReleasesFragmentContract
{
    interface View {}

    interface Presenter extends BasePresenter
    {
        void connectToBehaviorRelay(String searchFilter);

        void setupRecyclerView(RecyclerView recyclerView, FragmentActivity activity);
    }
}
