package bj.rxjavaexperimentation.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public interface RecyclerViewPresenter
{
    void getData(String id);

    void setupRecyclerView(Context context, RecyclerView recyclerView, String title);
}
