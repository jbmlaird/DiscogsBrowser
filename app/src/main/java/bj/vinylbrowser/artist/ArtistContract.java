package bj.vinylbrowser.artist;

import bj.vinylbrowser.common.DetailedView;
import bj.vinylbrowser.common.RecyclerViewPresenter;

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
