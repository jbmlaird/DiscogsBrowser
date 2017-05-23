package bj.vinylbrowser.wrappers;

import android.text.format.DateUtils;

/**
 * Created by Josh Laird on 18/04/2017.
 * For mocking.
 */
public class DateUtilsWrapper
{
    public CharSequence getRelativeTimeSpanString(long time, long now, long minResolution)
    {
        return DateUtils.getRelativeTimeSpanString(time, now, minResolution);
    }
}
