package bj.rxjavaexperimentation.label;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Module
public class LabelModule
{
    private LabelContract.View mView;

    public LabelModule(LabelContract.View view)
    {
        mView = view;
    }

    @Provides
    @Singleton
    LabelContract.View provideLabelView()
    {
        return mView;
    }
}
