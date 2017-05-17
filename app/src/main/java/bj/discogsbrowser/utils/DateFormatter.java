package bj.discogsbrowser.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;

import bj.discogsbrowser.wrappers.DateUtilsWrapper;
import bj.discogsbrowser.wrappers.SimpleDateFormatWrapper;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Created by Josh Laird on 17/04/2017.
 * <p>
 * Wrapper for formatting ISO dates into more readable Strings.
 */
public class DateFormatter
{
    private DateUtilsWrapper dateUtilsWrapper;
    private SimpleDateFormatWrapper simpleDateFormatWrapper;

    public DateFormatter(@NonNull DateUtilsWrapper dateUtilsWrapper, @NonNull SimpleDateFormatWrapper simpleDateFormatWrapper)
    {
        this.dateUtilsWrapper = dateUtilsWrapper;
        this.simpleDateFormatWrapper = simpleDateFormatWrapper;
    }

    public String formatIsoDate(String isoDate)
    {
        CharSequence relativeDateTimeString = "";
        try
        {
            relativeDateTimeString = dateUtilsWrapper.getRelativeTimeSpanString(simpleDateFormatWrapper.parse(isoDate).getTime(), System.currentTimeMillis(), DAY_IN_MILLIS);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return relativeDateTimeString.toString();
    }
}
