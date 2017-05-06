package bj.discogsbrowser.wrappers;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by Josh Laird on 19/04/2017.
 */

public class LogWrapper
{
    @Inject
    public LogWrapper()
    {

    }

    public void e(String string, String tag)
    {
        Log.e(string, tag);
    }
}
