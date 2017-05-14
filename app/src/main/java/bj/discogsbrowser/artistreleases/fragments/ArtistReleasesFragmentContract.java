package bj.discogsbrowser.artistreleases.fragments;

import bj.discogsbrowser.artistreleases.ArtistReleasesController;
import bj.discogsbrowser.common.BasePresenter;

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
