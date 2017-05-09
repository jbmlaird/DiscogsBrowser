package bj.discogsbrowser.utils;

import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.model.listing.Listing;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

/**
 * Created by Josh Laird on 08/05/2017.
 */

public class FilterHelper
{
    private String filterText = "";

    @Inject
    public FilterHelper()
    {

    }

    public void setFilterText(String filterText)
    {
        this.filterText = filterText;
    }

    public SingleTransformer<List<? extends RecyclerViewModel>, List<? extends RecyclerViewModel>> filterByFilterText()
    {
        return untransformed ->
                (Single) untransformed.flattenAsObservable(items -> items)
                        .filter(item ->
                                item.getSubtitle().toLowerCase().contains(filterText) || item.getTitle().toLowerCase().contains(filterText))
                        .toList();
    }

    public SingleTransformer<List<Listing>, List<Listing>> filterForSale()
    {
        return listingsSingle ->
                listingsSingle.flattenAsObservable(listings -> listings)
                        .filter(listing ->
                                listing.getStatus().equals("For Sale"))
                        .toList();
    }
}
