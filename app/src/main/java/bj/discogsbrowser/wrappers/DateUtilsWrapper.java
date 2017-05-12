package bj.discogsbrowser.wrappers;

import android.text.format.DateUtils;

import javax.inject.Inject;

/**
 * Created by Josh Laird on 18/04/2017.
 * <p>
 * For mocking getRelativeTimeSpanString static method.
 */
public class DateUtilsWrapper
{
    @Inject
    public DateUtilsWrapper()
    {
    }

    public CharSequence getRelativeTimeSpanString(long time, long now, long minResolution)
    {
        return DateUtils.getRelativeTimeSpanString(time, now, minResolution);
    }
}
