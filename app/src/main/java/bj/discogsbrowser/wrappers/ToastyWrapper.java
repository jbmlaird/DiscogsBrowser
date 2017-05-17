package bj.discogsbrowser.wrappers;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Created by Josh Laird on 11/05/2017.
 *
 * For mocking.
 */

public class ToastyWrapper
{
    private Context context;

    public void error(String string)
    {
        Toasty.error(context, string, Toast.LENGTH_SHORT, true).show();
    }
}
