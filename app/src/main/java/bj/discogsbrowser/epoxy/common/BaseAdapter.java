package bj.discogsbrowser.epoxy.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyAdapter;

/**
 * Created by Josh Laird on 17/04/2017.
 * <p>
 * https://github.com/airbnb/epoxy/wiki/Avoiding-Memory-Leaks
 */
public class BaseAdapter extends EpoxyAdapter
{
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        // This will force all models to be unbound and their views recycled once the RecyclerView is no longer in use. We need this so resources
        // are properly released, listeners are detached, and views can be returned to view pools (if applicable).
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager)
        {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).setRecycleChildrenOnDetach(true);
        }
    }
}
