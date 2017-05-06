package bj.discogsbrowser.label;

import bj.discogsbrowser.common.DetailedView;
import bj.discogsbrowser.common.RecyclerViewPresenter;

/**
 * Created by Josh Laird on 23/04/2017.
 */

public interface LabelContract
{
    interface View extends DetailedView
    {
        void displayRelease(String id, String title);
    }

    interface Presenter extends RecyclerViewPresenter
    {
    }
}
