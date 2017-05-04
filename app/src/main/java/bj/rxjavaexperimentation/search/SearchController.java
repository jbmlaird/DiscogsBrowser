package bj.rxjavaexperimentation.search;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.entity.SearchTerm;
import bj.rxjavaexperimentation.epoxy.common.CenterTextModel_;
import bj.rxjavaexperimentation.epoxy.common.ErrorModel_;
import bj.rxjavaexperimentation.epoxy.common.LoadingModel_;
import bj.rxjavaexperimentation.epoxy.search.PastSearchModel_;
import bj.rxjavaexperimentation.epoxy.search.SearchResultModel_;
import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.utils.AnalyticsTracker;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 25/04/2017.
 */
@Singleton
public class SearchController extends EpoxyController
{
    private Context context;
    private SearchContract.View mView;
    private ImageViewAnimator imageViewAnimator;
    private List<SearchResult> searchResults = new ArrayList<>();
    private List<SearchTerm> searchTerms;
    private boolean showPastSearches = true;
    private boolean showSearching = false;
    private boolean error = false;
    private AnalyticsTracker tracker;

    @Inject
    public SearchController(Context context, SearchContract.View mView, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        this.context = context;
        this.mView = mView;
        this.imageViewAnimator = imageViewAnimator;
        this.tracker = tracker;
    }

    @Override
    protected void buildModels()
    {
        if (showPastSearches)
            for (SearchTerm searchTerm : searchTerms)
                new PastSearchModel_()
                        .id("searchTerm " + searchTerms.indexOf(searchTerm))
                        .onClickListener(v -> mView.fillSearchBox(searchTerm.getSearchTerm()))
                        .searchTerm(searchTerm.getSearchTerm())
                        .addTo(this);
        else if (error)
            new ErrorModel_()
                    .id("error model")
                    .errorString("Unable to connect to server")
                    .onClick(v -> mView.retry())
                    .addTo(this);
        else if (showSearching)
            new LoadingModel_()
                    .id("searching model")
                    .imageViewAnimator(imageViewAnimator)
                    .addTo(this);
        else if (searchResults.size() == 0)
            new CenterTextModel_()
                    .text("No search results")
                    .id("no results")
                    .addTo(this);
        else
            for (SearchResult searchResult : searchResults)
                new SearchResultModel_()
                        .id("searchResult" + searchResults.indexOf(searchResult))
                        .context(context)
                        .title(searchResult.getTitle())
                        .subtitle(searchResult.getType())
                        .image(searchResult.getThumb())
                        .clickListener(v -> mView.startDetailedActivity(searchResult))
                        .addTo(this);
    }

    public void setResults(List<SearchResult> searchResults)
    {
        this.searchResults = searchResults;
        this.showPastSearches = false;
        this.showSearching = false;
        requestModelBuild();
    }

    public void setSearchTerms(List<SearchTerm> searchTerms)
    {
        this.searchTerms = searchTerms;
        this.showPastSearches = true;
        this.showSearching = false;
        this.error = false;
        requestModelBuild();
    }

    public void setShowPastSearches(boolean showPastSearches)
    {
        this.showPastSearches = showPastSearches;
        this.error = false;
        requestModelBuild();
    }

    public void setSearching(boolean showSearching)
    {
        this.showSearching = showSearching;
        if (showSearching)
        {
            this.showPastSearches = false;
            this.error = false;
        }
        requestModelBuild();
    }

    public void setError(boolean error)
    {
        this.error = error;
        if (error)
        {
            this.showPastSearches = false;
            this.showSearching = false;
            tracker.send(context.getString(R.string.search_activity), context.getString(R.string.search_activity), context.getString(R.string.error), "searchResults", 1L);
        }
        requestModelBuild();
    }

    public boolean getShowPastSearches()
    {
        return showPastSearches;
    }
}
