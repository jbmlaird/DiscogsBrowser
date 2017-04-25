package bj.rxjavaexperimentation.artist;

import bj.rxjavaexperimentation.common.RecyclerViewPresenter;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public interface ArtistContract
{
    interface View
    {
        void showMemberDetails(String name, String id);

        void showLink(String link);

        void showArtistReleases(String title, String id);
    }

    interface Presenter extends RecyclerViewPresenter
    {
        void unsubscribe();
    }
}
