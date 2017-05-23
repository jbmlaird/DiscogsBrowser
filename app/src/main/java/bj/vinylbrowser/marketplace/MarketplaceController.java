package bj.vinylbrowser.marketplace;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import bj.vinylbrowser.epoxy.common.DividerModel_;
import bj.vinylbrowser.epoxy.common.LoadingModel_;
import bj.vinylbrowser.epoxy.common.RetryModel_;
import bj.vinylbrowser.epoxy.common.WholeLineModel_;
import bj.vinylbrowser.epoxy.marketplace.MarketplaceModelCenter_;
import bj.vinylbrowser.epoxy.marketplace.MarketplaceModelTop_;
import bj.vinylbrowser.model.listing.Listing;
import bj.vinylbrowser.model.user.UserDetails;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.wrappers.NumberFormatWrapper;

/**
 * Created by Josh Laird on 09/05/2017.
 */
public class MarketplaceController extends EpoxyController
{
    private Context context;
    private final MarketplaceContract.View view;
    private final ImageViewAnimator imageViewAnimator;
    private boolean loading = true;
    private boolean error;
    private Listing listing;
    private String itemName = "";
    private String thumbUrl = "";
    private String price = "";
    private UserDetails sellerDetails = new UserDetails();
    private NumberFormatWrapper numberFormatWrapper;

    public MarketplaceController(Context context, MarketplaceContract.View view, ImageViewAnimator imageViewAnimator, NumberFormatWrapper numberFormatWrapper)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
        this.numberFormatWrapper = numberFormatWrapper;
    }

    @Override
    protected void buildModels()
    {
        new MarketplaceModelTop_()
                .imageViewAnimator(imageViewAnimator)
                .context(context)
                .itemName(itemName)
                .thumbUrl(thumbUrl)
                .price(price)
                .id("model top")
                .addTo(this);

        new DividerModel_()
                .id("top divider")
                .addTo(this);

        if (loading)
            new LoadingModel_()
                    .id("loading model")
                    .imageViewAnimator(imageViewAnimator)
                    .addTo(this);
        else if (error)
            new RetryModel_()
                    .errorString("Unable to load listing")
                    .id("retry model")
                    .onClick(v -> view.retry())
                    .addTo(this);

        else if (listing != null)
        {
            new MarketplaceModelCenter_()
                    .id("center model")
                    .mediaCondition(listing.getCondition())
                    .sleeveCondition(listing.getSleeveCondition())
                    .sellerUsername(listing.getSeller().getUsername())
                    .comments(listing.getComments())
                    .sellerRating(sellerDetails.getSellerRating())
                    .addTo(this);

            new DividerModel_()
                    .id("center divider")
                    .addTo(this);

            new WholeLineModel_()
                    .text("View seller shipping info")
                    .onClickListener(v -> view.viewSellerShipping(listing.getSeller().getShipping(), listing.getSeller().getUsername()))
                    .id("view seller shipping info")
                    .addTo(this);

            new DividerModel_()
                    .id("shipping info divider")
                    .addTo(this);

            new WholeLineModel_()
                    .onClickListener(v -> view.viewOnDiscogs(listing.getUri()))
                    .text("View on Discogs")
                    .id("model discogs")
                    .addTo(this);
        }
    }

    public void setListing(Listing listing)
    {
        this.listing = listing;
        this.loading = false;
        this.error = false;
        price = numberFormatWrapper.format(listing.getPrice().getValue());
        itemName = listing.getTitle();
        thumbUrl = listing.getThumb();
        requestModelBuild();
    }

    public void setLoading(boolean isLoading)
    {
        this.loading = isLoading;
        this.error = false;
        requestModelBuild();
    }

    public void setSellerDetails(UserDetails sellerDetails)
    {
        this.sellerDetails = sellerDetails;
        requestModelBuild();
    }

    public void setError(boolean error)
    {
        this.error = error;
        this.loading = false;
        requestModelBuild();
    }
}
