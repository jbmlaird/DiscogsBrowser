package bj.vinylbrowser.artistreleases.fragments;

import bj.vinylbrowser.artistreleases.ArtistReleasesController;
import bj.vinylbrowser.common.BasePresenter;

/**
 * Created by Josh Laird on 11/05/2017.
 */
public interface ArtistReleasesFragmentContract
{
    interface View
    {
        ArtistReleasesController getController();
    }

    interface Presenter extends BasePresenter
    {
        void connectToBehaviorRelay(String searchFilter);

        void bind(ArtistReleasesFragment fragment);
    }
}
