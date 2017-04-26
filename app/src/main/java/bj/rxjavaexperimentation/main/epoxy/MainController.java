package bj.rxjavaexperimentation.main.epoxy;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.main.MainContract;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.utils.DateFormatter;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;

/**
 * Created by Josh Laird on 17/04/2017.
 * <p>
 * Epoxy v2 Adapter controller.
 */
@Singleton
public class MainController extends EpoxyController
{
    private final String TAG = this.getClass().getSimpleName();
    private List<Order> orders = new ArrayList<>();
    private boolean loadingMorePurchases = true;
    private Context context;
    private MainContract.View mView;
    private SharedPrefsManager sharedPrefsManager;
    private ImageViewAnimator imageViewAnimator;
    private DateFormatter dateFormatter;
    private boolean loadingMoreSales = true;
    private List<Listing> listings = new ArrayList<>();

    @Inject
    public MainController(Context context, MainContract.View mView, SharedPrefsManager sharedPrefsManager, ImageViewAnimator imageViewAnimator, DateFormatter dateFormatter)
    {
        this.context = context;
        this.mView = mView;
        this.sharedPrefsManager = sharedPrefsManager;
        this.imageViewAnimator = imageViewAnimator;
        this.dateFormatter = dateFormatter;
    }

    @Override
    protected void buildModels()
    {
        new MainUserModel_(context)
                .id("user model")
                .username(sharedPrefsManager.getUsername())
                .avatarUrl(sharedPrefsManager.getAvatarUrl())
                .addTo(this);

        new DividerModel_()
                .id("divider3")
                .addTo(this);

        new MainHeaderModel_()
                .id("orders header")
                .title("Orders")
                .onClickListener(v -> mView.displayOrdersActivity())
                .addTo(this);

        new NoOrderModel_()
                .id("no orders")
                .text("No order history")
                .addIf(!loadingMorePurchases && orders.size() == 0, this);

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

        new MainHeaderModel_()
                .id("selling header")
                .title(context.getString(R.string.selling))
                .onClickListener(v -> mView.displayListingsActivity())
                .addTo(this);

        new NoOrderModel_()
                .id("not selling")
                .text("You're not currently selling anything")
                .addIf(!loadingMoreSales && listings.size() == 0, this);

        for (Listing listing : listings)
        {
            if (listings.indexOf(listing) == 5)
                break;

            new ListingModel_(dateFormatter)
                    .datePosted(listing.getPosted())
                    .releaseName(listing.getRelease().getDescription())
                    .onClickListener(v -> mView.displayListing(listing.getId()))
                    .id("listing" + String.valueOf(listings.indexOf(listing)))
                    .addTo(this);

            new DividerModel_()
                    .id("sale divider " + listings.indexOf(listing))
                    .addTo(this);
            // As it's the end of the page (for now) add a divider regardless
            // .addIf(listings.indexOf(listing) != listings.size() - 1, this);
        }

        new LoadingModel_()
                .id("sales loading model")
                .imageViewAnimator(imageViewAnimator)
                .addIf(loadingMoreSales, this);
    }

    public void setPurchases(List<Order> purchases)
    {
        this.orders = purchases;
        this.loadingMorePurchases = false;
        requestModelBuild();
    }

    public void setLoadingMorePurchases(boolean loadingMorePurchases)
    {
        this.loadingMorePurchases = loadingMorePurchases;
    }

    public void setSelling(List<Listing> listings)
    {
        this.listings = listings;
        this.loadingMoreSales = false;
        requestModelBuild();
    }

    public void setLoadingMoreSales(boolean loadingMoreSales)
    {
        this.loadingMoreSales = loadingMoreSales;
    }
}
