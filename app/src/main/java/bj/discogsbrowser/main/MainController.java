package bj.discogsbrowser.main;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.R;
import bj.discogsbrowser.epoxy.common.CarouselModel_;
import bj.discogsbrowser.epoxy.common.DividerModel_;
import bj.discogsbrowser.epoxy.common.EmptySpaceModel_;
import bj.discogsbrowser.epoxy.common.LoadingModel_;
import bj.discogsbrowser.epoxy.common.RetryModel_;
import bj.discogsbrowser.epoxy.main.InfoTextModel_;
import bj.discogsbrowser.epoxy.main.ListingModel_;
import bj.discogsbrowser.epoxy.main.MainTitleModel_;
import bj.discogsbrowser.epoxy.main.NoOrderModel_;
import bj.discogsbrowser.epoxy.main.OrderModel_;
import bj.discogsbrowser.epoxy.main.ViewedReleaseModel_;
import bj.discogsbrowser.greendao.ViewedRelease;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.DateFormatter;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.SharedPrefsManager;

/**
 * Created by Josh Laird on 17/04/2017.
 * <p>
 * Epoxy v2 Adapter controller.
 */
@ActivityScope
public class MainController extends EpoxyController
{
    private List<Order> orders = new ArrayList<>();
    private List<Listing> listings = new ArrayList<>();
    private List<ViewedRelease> viewedReleases = new ArrayList<>();
    private List<SearchResult> recommendations = new ArrayList<>();
    private Context context;
    private MainContract.View mView;
    private SharedPrefsManager sharedPrefsManager;
    private ImageViewAnimator imageViewAnimator;
    private DateFormatter dateFormatter;
    private AnalyticsTracker tracker;
    private boolean loadingMorePurchases = true;
    private boolean loadingMoreSales = true;
    private boolean ordersError;
    private boolean confirmEmail;
    private boolean viewedReleasesLoading = true;
    private boolean recommendationsLoading = true;
    private boolean recommendationsError;
    private boolean viewedReleasesError;

    @Inject
    public MainController(Context context, MainContract.View mView, SharedPrefsManager sharedPrefsManager,
                          ImageViewAnimator imageViewAnimator, DateFormatter dateFormatter, AnalyticsTracker tracker)
    {
        this.context = context;
        this.mView = mView;
        this.sharedPrefsManager = sharedPrefsManager;
        this.imageViewAnimator = imageViewAnimator;
        this.dateFormatter = dateFormatter;
        this.tracker = tracker;
    }

    @Override
    protected void buildModels()
    {
        new MainTitleModel_()
                .id("Viewed releases model")
                .title("Recently viewed")
                .size(0) // Hide see all button
                .addTo(this);

        if (viewedReleasesLoading)
            new LoadingModel_()
                    .imageViewAnimator(imageViewAnimator)
                    .id("viewed releases loading")
                    .addTo(this);
        else if (viewedReleasesError)
            new RetryModel_()
                    .onClick(v -> mView.retryHistory())
                    .errorString("Unable to load recently viewed")
                    .id("viewed releases error")
                    .addTo(this);
        else
        {
            if (viewedReleases.size() > 0)
            {
                List<ViewedReleaseModel_> viewedReleaseModels = new ArrayList<>();
                for (ViewedRelease viewedRelease : viewedReleases)
                {
                    viewedReleaseModels.add(new ViewedReleaseModel_()
                            .id("viewedReleases" + viewedReleases.indexOf(viewedRelease))
                            .context(context)
                            .onClickListener(v -> mView.displayRelease(viewedRelease.getReleaseName(), viewedRelease.getReleaseId()))
                            .thumbUrl(viewedRelease.getThumbUrl())
                            .releaseName(viewedRelease.getArtists() + " - " + viewedRelease.getReleaseName()));
                }

                add(new CarouselModel_()
                        .id("viewed release carousel")
                        .models(viewedReleaseModels));
            }
            else
                new InfoTextModel_()
                        .infoText("You haven't viewed any releases")
                        .id("not viewed any releases model")
                        .addTo(this);
        }

        new MainTitleModel_()
                .title("You might like")
                .id("Recommendations model")
                .tvButtonText("Learn more")
                .onClickListener(v -> mView.learnMore())
                .size(recommendations.size())
                .addTo(this);

        if (recommendationsLoading)
            new LoadingModel_()
                    .imageViewAnimator(imageViewAnimator)
                    .id("recommendations loading")
                    .addTo(this);
        else if (recommendationsError)
            new RetryModel_()
                    .onClick(v -> mView.retryRecommendations())
                    .errorString("Unable to build recommendations")
                    .id("recommendations error")
                    .addTo(this);
        else
        {
            if (recommendations.size() > 0)
            {
                List<ViewedReleaseModel_> viewedReleaseModels = new ArrayList<>();
                for (SearchResult recommendation : recommendations)
                {
                    viewedReleaseModels.add(new ViewedReleaseModel_()
                            .id("recommendations" + recommendations.indexOf(recommendation))
                            .context(context)
                            .onClickListener(v -> mView.displayRelease(recommendation.getTitle(), recommendation.getId()))
                            .thumbUrl(recommendation.getThumb())
                            .releaseName(recommendation.getTitle()));

                }
                add(new CarouselModel_()
                        .id("recommendations carousel")
                        .models(viewedReleaseModels));
            }
            else
                new InfoTextModel_()
                        .infoText("No recommendations until you've viewed a release")
                        .id("not viewed any releases recommendations model")
                        .addTo(this);
        }

        new MainTitleModel_()
                .id("orders header")
                .title("Orders")
                .size(orders.size())
                .onClickListener(v -> mView.displayOrdersActivity(sharedPrefsManager.getUsername()))
                .addTo(this);

        new RetryModel_()
                .id("orders error")
                .onClick(v -> mView.retry())
                .errorString("Unable to fetch orders")
                .addIf(ordersError, this);

        new InfoTextModel_()
                .id("confirm email model")
                .infoText(context.getString(R.string.verify_email))
                .addIf(confirmEmail, this);

        new NoOrderModel_()
                .id("no orders")
                .text("No order history")
                .addIf(!loadingMorePurchases && orders.size() == 0 && !ordersError && !confirmEmail, this);

        for (Order order : orders)
        {
            // Only display a maximum of 5
            if (orders.indexOf(order) == 5)
                break;

            new OrderModel_(dateFormatter)
                    .lastActivity(order.getLastActivity())
                    .status(order.getStatus())
                    .buyer(order.getBuyer().getUsername())
                    .onClickListener(v -> mView.displayOrder(order.getId()))
                    .id("order" + String.valueOf(orders.indexOf(order)))
                    .addTo(this);

            new DividerModel_()
                    .id("order divider " + orders.indexOf(order))
                    .addIf(orders.indexOf(order) != orders.size() - 1, this);
        }

        new LoadingModel_()
                .imageViewAnimator(imageViewAnimator)
                .id("loading model")
                .addIf(loadingMorePurchases, this);

        // Selling

        new MainTitleModel_()
                .id("selling header")
                .title(context.getString(R.string.selling))
                .size(listings.size())
                .onClickListener(v -> mView.displayListingsActivity(sharedPrefsManager.getUsername()))
                .addTo(this);

        new RetryModel_()
                .id("selling error")
                .errorString("Unable to fetch selling")
                .onClick(v -> mView.retry())
                .addIf(ordersError, this);

        new NoOrderModel_()
                .id("not selling")
                .text(context.getString(R.string.selling_none))
                .addIf(!loadingMoreSales && listings.size() == 0 && !ordersError && !confirmEmail, this);

        new InfoTextModel_()
                .id("confirm email model2")
                .infoText(context.getString(R.string.verify_email))
                .addIf(confirmEmail, this);

        new LoadingModel_()
                .id("sales loading model")
                .imageViewAnimator(imageViewAnimator)
                .addIf(loadingMoreSales, this);

        for (Listing listing : listings)
        {
            if (listings.indexOf(listing) == 5)
                break;

            new ListingModel_(dateFormatter)
                    .datePosted(listing.getPosted())
                    .releaseName(listing.getRelease().getDescription())
                    .onClickListener(v -> mView.displayListing(listing.getId(), listing.getTitle(), sharedPrefsManager.getUsername(), "", listing.getSeller().getUsername()))
                    .id("listing" + String.valueOf(listings.indexOf(listing)))
                    .addTo(this);

            new DividerModel_()
                    .id("sale divider " + listings.indexOf(listing))
                    .addTo(this);
        }

        new EmptySpaceModel_()
                .id("bottom of main")
                .addTo(this);
    }

    public void setOrders(List<Order> purchases)
    {
        this.orders = purchases;
        this.loadingMorePurchases = false;
        this.ordersError = false;
        this.confirmEmail = false;
        requestModelBuild();
    }

    public void setLoadingMorePurchases(boolean loadingMorePurchases)
    {
        this.loadingMorePurchases = loadingMorePurchases;
        this.loadingMoreSales = loadingMorePurchases;
        this.ordersError = false;
        this.confirmEmail = false;
        requestModelBuild();
    }

    public void setSelling(List<Listing> listings)
    {
        this.listings = listings;
        this.loadingMoreSales = false;
        this.confirmEmail = false;
        requestModelBuild();
    }

    public void setOrdersError(boolean b)
    {
        this.ordersError = b;
        this.loadingMorePurchases = false;
        this.loadingMoreSales = false;
        this.confirmEmail = false;
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.error), "fetching orders", 1L);
        requestModelBuild();
    }

    public void setConfirmEmail(boolean confirmEmail)
    {
        this.confirmEmail = confirmEmail;
        this.ordersError = false;
        this.loadingMorePurchases = false;
        this.loadingMoreSales = false;
        requestModelBuild();
    }

    public void setLoadingViewedReleases(boolean loadingViewedReleases)
    {
        this.viewedReleasesLoading = loadingViewedReleases;
        this.viewedReleasesError = false;
        requestModelBuild();
    }

    public void setViewedReleases(List<ViewedRelease> viewedReleases)
    {
        this.viewedReleases = viewedReleases;
        this.viewedReleasesLoading = false;
        this.viewedReleasesError = false;
        requestModelBuild();
    }

    public void setLoadingRecommendations(boolean loadingRecommendations)
    {
        this.recommendationsLoading = loadingRecommendations;
        this.recommendationsError = false;
        requestModelBuild();
    }

    public void setRecommendations(List<SearchResult> recommendations)
    {
        this.recommendations = recommendations;
        this.recommendationsLoading = false;
        this.recommendationsError = false;
        requestModelBuild();
    }

    public void setRecommendationsError(boolean recommendationsError)
    {
        this.recommendationsError = recommendationsError;
        this.recommendationsLoading = false;
        requestModelBuild();
    }

    // This will only happen if the database gets corrupted
    public void setViewedReleasesError(boolean viewedReleasesError)
    {
        this.viewedReleasesError = viewedReleasesError;
        this.viewedReleasesLoading = false;
        requestModelBuild();
    }
}
