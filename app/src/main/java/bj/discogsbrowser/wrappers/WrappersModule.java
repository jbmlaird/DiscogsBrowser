package bj.discogsbrowser.wrappers;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 13/05/2017.
 * <p>
 * Module to provide the Wrappers.
 */
@Module
public class WrappersModule
{
    @Provides
    protected SimpleDateFormatWrapper provideSdfWrapper()
    {
        return new SimpleDateFormatWrapper();
    }

    @Provides
    protected NumberFormatWrapper provideNumberFormat()
    {
        return new NumberFormatWrapper();
    }

    @Provides
    protected ToastyWrapper provideToasty()
    {
        return new ToastyWrapper();
    }

    @Provides
    protected DateUtilsWrapper provideDateUtils()
    {
        return new DateUtilsWrapper();
    }

    @Provides
    protected RxSocialConnectWrapper provideRxSocial()
    {
        return new RxSocialConnectWrapper();
    }

    @Provides
    protected LogWrapper provideLogWrapper()
    {
        return new LogWrapper();
    }
}
