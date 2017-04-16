package bj.rxjavaexperimentation.singlelist;

import javax.inject.Inject;

import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;

/**
 * Created by Josh Laird on 16/04/2017.
 */

public class SingleListPresenter implements SingleListContract.Presenter
{
    private SingleListContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;

    @Inject
    public SingleListPresenter(SingleListContract.View view, SearchDiscogsInteractor searchDiscogsInteractor)
    {
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
    }

    @Override
    public void getData(String type, String username)
    {
        switch (type)
        {
            case "wantlist":
                searchDiscogsInteractor.fetchWantlist(username);
                break;
            case "collection":
                searchDiscogsInteractor.fetchCollection(username);
                break;
        }
    }

    private void filterResults()
    {
        view.filterIntent();
    }
}
