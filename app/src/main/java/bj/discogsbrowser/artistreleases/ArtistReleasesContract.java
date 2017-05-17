package bj.discogsbrowser.artistreleases;

import bj.discogsbrowser.common.SingleListView;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 10/04/2017.
 */

public interface ArtistReleasesContract
{
    interface View extends SingleListView
    {
        Observable<CharSequence> filterIntent();
    }

    interface Presenter
    {
        void fetchArtistReleases(String id);

        void setupFilter();
    }
}
