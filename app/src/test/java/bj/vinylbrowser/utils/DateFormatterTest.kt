package bj.vinylbrowser.utils

import bj.vinylbrowser.wrappers.DateUtilsWrapper
import bj.vinylbrowser.wrappers.SimpleDateFormatWrapper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException
import java.util.*

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class DateFormatterTest {
    val isoDate = "2015-04-24T21:48:23-07:00"
    lateinit var dateFormatter: DateFormatter
    val dateUtilsWrapper: DateUtilsWrapper = mock()
    val simpleDateFormatWrapper: SimpleDateFormatWrapper = mock()

    @Before
    fun setup() {
        dateFormatter = DateFormatter(dateUtilsWrapper, simpleDateFormatWrapper)
    }

    @Test
    @Throws(ParseException::class)
    fun formatValidString_returnsFormattedString() {
        whenever(simpleDateFormatWrapper.parse(isoDate)).thenReturn(Date(1429937303000L))
        whenever(dateUtilsWrapper.getRelativeTimeSpanString(any<Long>(Long::class.java), any<Long>(Long::class.java), any<Long>(Long::class.java))).thenReturn("15 Apr 2015")

        assertEquals("15 Apr 2015", dateFormatter.formatIsoDate(isoDate))

        verify(simpleDateFormatWrapper, times(1)).parse(isoDate)
        verify(dateUtilsWrapper, times(1)).getRelativeTimeSpanString(any<Long>(Long::class.java), any<Long>(Long::class.java), any<Long>(Long::class.java))
    }

    @Test
    @Throws(ParseException::class)
    fun formatInvalidString_returnsEmptyString() {
        whenever(simpleDateFormatWrapper.parse("canBeAnything")).thenThrow(ParseException::class.java)

        assertEquals("", dateFormatter.formatIsoDate("canBeAnything"))

        verify(simpleDateFormatWrapper, times(1)).parse("canBeAnything")
    }
}