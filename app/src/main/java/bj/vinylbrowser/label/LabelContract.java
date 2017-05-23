package bj.vinylbrowser.label;

import bj.vinylbrowser.common.DetailedView;
import bj.vinylbrowser.common.RecyclerViewPresenter;

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
