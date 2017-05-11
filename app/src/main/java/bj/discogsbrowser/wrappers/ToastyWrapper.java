package bj.discogsbrowser.wrappers;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

/**
 * Created by Josh Laird on 11/05/2017.
 */

public class ToastyWrapper
{
    private Context context;

    @Inject
    public ToastyWrapper(Context context)
    {
        this.context = context;
    }

    public void error(String string)
    {
        Toasty.error(context, string, Toast.LENGTH_SHORT, true).show();
    }
}
