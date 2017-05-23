package bj.vinylbrowser.wrappers;

import java.text.NumberFormat;

/**
 * Created by Josh Laird on 09/05/2017.
 * For mocking.
 */
public class NumberFormatWrapper
{
    public String format(Double doubleToFormat)
    {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(doubleToFormat);
    }
}
