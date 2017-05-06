package bj.discogsbrowser.master;

import bj.discogsbrowser.common.RecyclerViewPresenter;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public interface MasterContract
{
    interface View
    {
        void displayRelease(String title, String id);

        void retry();
    }

    interface Presenter extends RecyclerViewPresenter {}
}
