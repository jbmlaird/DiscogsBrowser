package bj.discogsbrowser.artist;

import bj.discogsbrowser.common.DetailedView;
import bj.discogsbrowser.common.RecyclerViewPresenter;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public interface ArtistContract
{
    interface View extends DetailedView
    {
        void showMemberDetails(String name, String id);

        void showArtistReleases(String title, String id);
    }

    interface Presenter extends RecyclerViewPresenter
    {
    }
}
