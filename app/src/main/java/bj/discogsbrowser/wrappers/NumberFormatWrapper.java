package bj.discogsbrowser.wrappers;

import java.text.NumberFormat;

import javax.inject.Inject;

/**
 * Created by Josh Laird on 09/05/2017.
 */

public class NumberFormatWrapper
{
    @Inject
    public NumberFormatWrapper()
    {
    }

    public String format(Double doubleToFormat)
    {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(doubleToFormat);
    }
}
