package bj.rxjavaexperimentation.main;

import bj.rxjavaexperimentation.common.BasePresenter;

/**
 * Created by j on 18/02/2017.
 */

public interface MainContract
{
    interface View
    {
        void setSuccessText(String successText);
    }

    interface Presenter extends BasePresenter<View>
    {

    }
}
