package bj.discogsbrowser.wrappers;

import android.content.Context;

import bj.discogsbrowser.utils.DateFormatter;
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
    protected DateUtilsWrapper dateUtilsWrapper()
    {
        return new DateUtilsWrapper();
    }

    @Provides
    protected SimpleDateFormatWrapper provideSdfWrapper()
    {
        return new SimpleDateFormatWrapper();
    }

    @Provides
    protected DateFormatter provideDateFormatter(DateUtilsWrapper dateUtilsWrapper, SimpleDateFormatWrapper simpleDateFormatWrapper)
    {
        return new DateFormatter(dateUtilsWrapper, simpleDateFormatWrapper);
    }

    @Provides
    protected NumberFormatWrapper provideNumberFormat()
    {
        return new NumberFormatWrapper();
    }

    @Provides
    protected ToastyWrapper provideToasty(Context context)
    {
        return new ToastyWrapper(context);
    }
}
