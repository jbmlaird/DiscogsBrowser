package bj.discogsbrowser.utils;

import javax.inject.Inject;

import bj.discogsbrowser.model.common.RecyclerViewModel;
import io.reactivex.functions.Predicate;

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

    public Predicate<? super RecyclerViewModel> filterRecyclerViewModel()
    {
        return listingPredicate ->
                listingPredicate.getSubtitle().toLowerCase().contains(filterText) || listingPredicate.getTitle().toLowerCase().contains(filterText);
    }
}
