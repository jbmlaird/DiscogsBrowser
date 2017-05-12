package bj.discogsbrowser.search;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 20/02/2017.
 */
@ActivityScope
@Component(modules = {SearchModule.class}, dependencies = {AppComponent.class})
public interface SearchComponent
{
    void inject(SearchActivity searchActivity);
}
