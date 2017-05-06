package bj.discogsbrowser.search;

import javax.inject.Singleton;

import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 20/02/2017.
 */

@Singleton
@Component(modules = {SearchModule.class}, dependencies = {AppComponent.class})
public interface SearchComponent
{
    void inject(SearchActivity searchActivity);
}
