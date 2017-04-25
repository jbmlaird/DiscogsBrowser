package bj.rxjavaexperimentation.search.epoxy;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import bj.rxjavaexperimentation.epoxy.common.BaseAdapter;
import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.search.SearchPresenter;

/**
 * Created by Josh Laird on 07/04/2017.
 */
public class ResultsAdapter extends BaseAdapter
{
    private Context context;
    private SearchPresenter searchPresenter;

    public ResultsAdapter(Context context, SearchPresenter searchPresenter)
    {
        this.context = context;
        this.searchPresenter = searchPresenter;
    }

    public void addResults(ArrayList<SearchResult> searchResults)
    {
        for (SearchResult searchResult : searchResults)
        {
            addModel(new SearchResultModel_(context)
                    .title(searchResult.getTitle())
                    .subtitle(searchResult.getType())
                    .image(searchResult.getThumb())
                    .clickListener(onSearchResultClickListener(searchResult, searchResults.indexOf(searchResult))));
        }
    }

    public void clearResults()
    {
        removeAllModels();
    }

    private View.OnClickListener onSearchResultClickListener(SearchResult searchResult, int index)
    {
        return (v -> searchPresenter.viewDetailed(searchResult));
    }
}
