package bj.rxjavaexperimentation.search;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.entity.SearchTerm;
import bj.rxjavaexperimentation.epoxy.common.CenterTextModel_;
import bj.rxjavaexperimentation.epoxy.search.PastSearchModel_;
import bj.rxjavaexperimentation.epoxy.search.SearchResultModel_;
import bj.rxjavaexperimentation.model.search.SearchResult;

/**
 * Created by Josh Laird on 25/04/2017.
 */
@Singleton
public class SearchController extends EpoxyController
{
    private Context context;
    private SearchContract.View mView;
    private List<SearchResult> searchResults = new ArrayList<>();
    private List<SearchTerm> searchTerms;
    private boolean noResults;
    private boolean showPastSearches = true;

    @Inject
    public SearchController(Context context, SearchContract.View mView)
    {
        this.context = context;
        this.mView = mView;
    }

    @Override
    protected void buildModels()
    {
        if (showPastSearches)
            for (SearchTerm searchTerm : searchTerms)
            {
                new PastSearchModel_()
                        .id("searchTerm " + searchTerms.indexOf(searchTerm))
                        .onClickListener(v -> mView.fillSearchBox(searchTerm.getSearchTerm()))
                        .searchTerm(searchTerm.getSearchTerm())
                        .addTo(this);
            }
        else if (searchResults.size() == 0)
            new CenterTextModel_()
                    .text("No search results")
                    .id("no results")
                    .addTo(this);
        else if (searchResults.size() == 1 && searchResults.get(0).getId().equals("bj"))
        {
            // Remove the search terms as the user is searching. ProgressBar displays.
        }
        else
            for (SearchResult searchResult : searchResults)
            {
                new SearchResultModel_()
                        .id("searchResult" + searchResults.indexOf(searchResult))
                        .context(context)
                        .title(searchResult.getTitle())
                        .subtitle(searchResult.getType())
                        .image(searchResult.getThumb())
                        .clickListener(v -> mView.startDetailedActivity(searchResult))
                        .addTo(this);
            }
    }

    public void setResults(List<SearchResult> searchResults)
    {
        this.searchResults = searchResults;
        requestModelBuild();
    }

    public void clearResults()
    {
        searchResults.clear();
        requestModelBuild();
    }

    public void setSearchTerms(List<SearchTerm> searchTerms)
    {
        this.searchTerms = searchTerms;
        requestModelBuild();
    }

    public void setShowPastSearches(boolean showPastSearches)
    {
        this.showPastSearches = showPastSearches;
    }
}
