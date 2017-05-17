package bj.discogsbrowser.wrappers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Josh Laird on 18/04/2017.
 * For mocking.
 */
public class SimpleDateFormatWrapper
{
    private static final String discogsDateFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
    private DateFormat simpleDateFormat = new SimpleDateFormat(discogsDateFormat, Locale.getDefault());

    public Date parse(String date) throws ParseException
    {
        return simpleDateFormat.parse(date);
    }
}
