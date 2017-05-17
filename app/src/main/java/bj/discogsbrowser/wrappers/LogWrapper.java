package bj.discogsbrowser.wrappers;

import android.util.Log;

/**
 * Created by Josh Laird on 19/04/2017.
 * For mocking.
 */
public class LogWrapper
{
    public void e(String string, String tag)
    {
        Log.e(string, tag);
    }
}
