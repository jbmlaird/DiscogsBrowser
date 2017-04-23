package bj.rxjavaexperimentation.release;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.common.BaseActivity;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class ReleaseActivity extends BaseActivity implements ReleaseContract.View
{
    @Inject ReleasePresenter presenter;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        ReleaseComponent component = DaggerReleaseComponent.builder()
                .appComponent(appComponent)
                .releaseModule(new ReleaseModule(this))
                .build();

        component.inject(this);
    }
}
