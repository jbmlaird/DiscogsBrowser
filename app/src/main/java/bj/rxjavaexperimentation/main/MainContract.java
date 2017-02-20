package bj.rxjavaexperimentation.main;

import bj.rxjavaexperimentation.common.BasePresenter;
import bj.rxjavaexperimentation.common.BaseView;

/**
 * Created by j on 18/02/2017.
 */

public interface MainContract
{
    interface View extends BaseView
    {

    }

    interface Presenter extends BasePresenter<View>
    {

    }
}
