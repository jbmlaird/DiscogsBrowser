package bj.rxjavaexperimentation;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by j on 18/02/2017.
 */
@Module
public class AppModule
{
    private Context mContext;

    public AppModule(Context context)
    {
        mContext = context;
    }

    @Provides
    Context provideContext()
    {
        return mContext;
    }
}
