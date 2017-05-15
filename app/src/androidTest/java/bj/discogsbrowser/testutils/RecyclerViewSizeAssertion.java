package bj.discogsbrowser.testutils;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import bj.discogsbrowser.common.MyRecyclerView;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 15/05/2017.
 * <p>
 * From: http://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso
 */
public class RecyclerViewSizeAssertion implements ViewAssertion
{
    private final int wantedSize;

    public RecyclerViewSizeAssertion(int wantedSize)
    {
        this.wantedSize = wantedSize;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException)
    {
        if (noViewFoundException != null)
            throw noViewFoundException;

        RecyclerView.Adapter adapter = ((MyRecyclerView) view).getAdapter();
        assertEquals(adapter.getItemCount(), wantedSize);
    }
}
