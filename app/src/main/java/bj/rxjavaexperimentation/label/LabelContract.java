package bj.rxjavaexperimentation.label;

import bj.rxjavaexperimentation.common.DetailedView;
import bj.rxjavaexperimentation.common.RecyclerViewPresenter;

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
