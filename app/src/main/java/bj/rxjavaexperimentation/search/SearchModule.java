package bj.rxjavaexperimentation.search;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 20/02/2017.
 */

@Module
public class SearchModule
{
    private SearchContract.View mView;

    public SearchModule(SearchContract.View view)
    {
        mView = view;
    }

    @Provides
    SearchContract.View providesSearchView()
    {
        return mView;
    }
}
