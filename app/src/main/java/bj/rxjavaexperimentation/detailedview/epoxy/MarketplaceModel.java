package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.listing.MyListing;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josh Laird on 13/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_marketplace)
public abstract class MarketplaceModel extends EpoxyModel<LinearLayout>
{
    private final LayoutInflater layoutInflater;
    private DetailedAdapter adapter;
    private final Context context;
    @EpoxyAttribute List<MyListing> myListings = new ArrayList<>();
    @EpoxyAttribute String numberForSale;
    @EpoxyAttribute String lowestPrice;
    @EpoxyAttribute String title;
    @BindView(R.id.lytMarketplaceContainer) LinearLayout lytMarketplaceContainer;
    @BindView(R.id.lytMarketplaceHeader) LinearLayout lytMarketplaceHeader;
    @BindView(R.id.lytMarketplaceList) LinearLayout lytMarketplaceList;
    @BindView(R.id.tvMarketplaceSummary) TextView tvMarketplaceSummary;
    @BindView(R.id.tvMarketplaceHeader) TextView tvMarketplaceHeader;
    @BindView(R.id.pbMarketplaceHeader) ProgressBar pbMarketplaceHeader;
    @BindView(R.id.tvViewMore) TextView tvViewMore;

    public MarketplaceModel(Context context, DetailedAdapter adapter)
    {
        this.context = context;
        this.adapter = adapter;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvMarketplaceSummary.setText(" - " + numberForSale + " 12\" from " + lowestPrice);
        if (myListings.size() > 0)
        {
            lytMarketplaceHeader.setVisibility(View.GONE);
            lytMarketplaceList.setVisibility(View.VISIBLE);
            for (MyListing myListing : myListings)
            {
                inflateMarketplaceItem(myListings.indexOf(myListing));
                if (myListings.indexOf(myListing) == 2 && myListings.size() > 3)
                {
                    tvViewMore.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    @OnClick(R.id.tvViewMore)
    public void viewMoreListings()
    {
        for (int i = 3; i < myListings.size(); i++)
        {
            inflateMarketplaceItem(i);
        }
        tvViewMore.setVisibility(View.GONE);
    }

    private void inflateMarketplaceItem(int i)
    {
        View listingView = layoutInflater.inflate(R.layout.item_listing, lytMarketplaceList, false);
        ListingViewHolder listingViewHolder = new ListingViewHolder(listingView);
        listingViewHolder.tvSleeve.setText("{fa-inbox} " + myListings.get(i).getSleeveCondition());
        listingViewHolder.tvMedia.setText("{fa-music} " + myListings.get(i).getMediaCondition());
        listingViewHolder.tvShipsFrom.setText(myListings.get(i).getShipsFrom());
        listingViewHolder.tvSeller.setText(myListings.get(i).getSellerName());
        listingViewHolder.tvRating.setText(myListings.get(i).getSellerRating());
        listingViewHolder.tvConvertedPrice.setText(myListings.get(i).getConvertedPrice());
        listingViewHolder.tvListedPrice.setText(myListings.get(i).getPrice());
        listingViewHolder.lytMarketplaceItemContainer.setOnClickListener(getMarketplaceOnClickListener(myListings.get(i)));
        lytMarketplaceList.addView(listingView);
    }

    public View.OnClickListener getMarketplaceOnClickListener(MyListing myListing)
    {
        return v ->
                adapter.displayListingInformation(myListing);
    }

    public void listingsError()
    {
        pbMarketplaceHeader.setVisibility(View.GONE);
        tvMarketplaceHeader.setText("Error getting listings");
    }

    public void noListings()
    {
        pbMarketplaceHeader.setVisibility(View.GONE);
        tvMarketplaceHeader.setText("No current listings");
    }

    class ListingViewHolder
    {
        @BindView(R.id.lytMarketplaceItemContainer) ConstraintLayout lytMarketplaceItemContainer;
        @BindView(R.id.tvSleeve) IconTextView tvSleeve;
        @BindView(R.id.tvMedia) IconTextView tvMedia;
        @BindView(R.id.tvShipsFrom) TextView tvShipsFrom;
        @BindView(R.id.tvSeller) TextView tvSeller;
        @BindView(R.id.tvRating) TextView tvRating;
        @BindView(R.id.tvConvertedPrice) TextView tvConvertedPrice;
        @BindView(R.id.tvListedPrice) TextView tvListedPrice;

        public ListingViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
