package bj.rxjavaexperimentation.search;

import android.content.Context;

import com.airbnb.epoxy.EpoxyAdapter;

import java.util.ArrayList;

import bj.rxjavaexperimentation.model.search.SearchResult;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public class ResultsAdapter extends EpoxyAdapter
{
    private Context context;

    public ResultsAdapter(Context context)
    {
        this.context = context;
    }

    public void addResults(ArrayList<SearchResult> searchResults)
    {
        for (SearchResult searchResult : searchResults)
        {
            addModel(new SearchResultModel_(context)
                    .title(searchResult.getTitle())
                    .subtitle(searchResult.getType())
                    .image(searchResult.getThumb()));
        }
    }

    public void clearResults()
    {
        removeAllModels();
    }
}
