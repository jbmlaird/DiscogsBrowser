package bj.rxjavaexperimentation.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Date;

import bj.rxjavaexperimentation.utils.wrapper.DateUtilsWrapper;
import bj.rxjavaexperimentation.utils.wrapper.SimpleDateFormatWrapper;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 17/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DateFormatterTest
{
    private static final String isoDate = "2015-04-24T21:48:23-07:00";
    private DateFormatter dateFormatter;
    @Mock DateUtilsWrapper dateUtilsWrapper;
    @Mock SimpleDateFormatWrapper simpleDateFormatWrapper;

    @Before
    public void setup()
    {
        dateFormatter = new DateFormatter(dateUtilsWrapper, simpleDateFormatWrapper);
    }

    @Test
    public void formatValidString_returnsFormattedString() throws ParseException
    {
        when(simpleDateFormatWrapper.parse(isoDate)).thenReturn(new Date(1429937303000L));
        when(dateUtilsWrapper.getRelativeTimeSpanString(any(Long.class), any(Long.class), any(Long.class))).thenReturn("15 Apr 2015");

        assertEquals("15 Apr 2015", dateFormatter.formatIsoDate(isoDate));

        verify(simpleDateFormatWrapper, times(1)).parse(isoDate);
        verify(dateUtilsWrapper, times(1)).getRelativeTimeSpanString(any(Long.class), any(Long.class), any(Long.class));
    }

    @Test
    public void formatInvalidString_returnsEmptyString() throws ParseException
    {
        when(simpleDateFormatWrapper.parse("canBeAnything")).thenThrow(ParseException.class);

        assertEquals("", dateFormatter.formatIsoDate("canBeAnything"));

        verify(simpleDateFormatWrapper, times(1)).parse("canBeAnything");
    }
}