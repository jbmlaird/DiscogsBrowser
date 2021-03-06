package bj.vinylbrowser.search;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import bj.vinylbrowser.R;
import bj.vinylbrowser.epoxy.common.CenterTextModel_;
import bj.vinylbrowser.epoxy.common.LoadingModel_;
import bj.vinylbrowser.epoxy.common.RetryModel_;
import bj.vinylbrowser.epoxy.search.PastSearchModel_;
import bj.vinylbrowser.epoxy.search.SearchResultModel_;
import bj.vinylbrowser.greendao.SearchTerm;
import bj.vinylbrowser.model.search.SearchResult;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;

/**
 * Created by Josh Laird on 25/04/2017.
 */
public class SearchEpxController extends EpoxyController
{
    private Context context;
    private SearchContract.View mView;
    private ImageViewAnimator imageViewAnimator;
    List<SearchResult> searchResults = new ArrayList<>();
    private List<SearchTerm> searchTerms = new ArrayList<>();
    private boolean showPastSearches = true;
    private boolean showSearching = false;
    private boolean error = false;
    private AnalyticsTracker tracker;

    public SearchEpxController(Context context, SearchContract.View mView, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
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
            new RetryModel_()
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

    public void setSearchResults(List<SearchResult> searchResults)
    {
        this.searchResults = searchResults;
        this.showPastSearches = false;
        this.showSearching = false;
        requestModelBuild();
    }

    public void setPastSearches(List<SearchTerm> searchTerms)
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
            tracker.send(context.getString(R.string.search_activity), context.getString(R.string.search_activity), context.getString(R.string.error), "searchResults", "1");
        }
        requestModelBuild();
    }

    public boolean getShowPastSearches()
    {
        return showPastSearches;
    }

    public boolean getSearching()
    {
        return showSearching;
    }
}
