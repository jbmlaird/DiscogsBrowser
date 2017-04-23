package bj.rxjavaexperimentation.master;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.common.BaseActivity;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class MasterActivity extends BaseActivity implements MasterContract.View
{
    @Inject MasterPresenter masterPresenter;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        MasterComponent component = DaggerMasterComponent.builder()
                .appComponent(appComponent)
                .masterModule(new MasterModule(this))
                .build();

        component.inject(this);
    }
}
