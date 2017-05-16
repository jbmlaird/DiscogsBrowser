package bj.discogsbrowser.search;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.R;
import bj.discogsbrowser.artist.ArtistActivity;
import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.master.MasterActivity;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.release.ReleaseActivity;
import bj.discogsbrowser.testutils.EspressoDaggerMockRule;
import bj.discogsbrowser.testutils.TestUtils;
import bj.discogsbrowser.utils.ImageViewAnimator;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static bj.discogsbrowser.testutils.EspressoDaggerMockRule.getApp;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 16/05/2017.
 * <p>
 * TODO: Real network tests.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class SearchActivityTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<SearchActivity> mActivityTestRule = new IntentsTestRule<>(SearchActivity.class, false, false);
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock DaoManager daoManager;
    @Mock Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchFunction;
    private List<SearchResult> results = SearchResultFactory.getResults();
    private SearchActivity activity;
    private SearchController controller;
    private String searchQuery = "yeeeeboi";
    private Intent startIntent;
    private String searchTermText = "search term";

    @Before
    public void setUp()
    {
        startIntent = SearchActivity.createIntent(getApp());
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
    }

    @Test
    public void clickingRecentSearch_fillsBox()
    {
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setSearchTerm(searchTermText);
        when(daoManager.getRecentSearchTerms()).thenReturn(Collections.singletonList(searchTerm));

        activity = mActivityTestRule.launchActivity(startIntent);
        controller = activity.controller;

        onView(withText(searchTermText)).perform(click());
        onView(withId(R.id.search_src_text)).check(new TestUtils.isEditTextEqualTo(searchTerm.getSearchTerm()));
        assertEquals(controller.getAdapter().getItemCount(), 1);
    }

    @Test
    public void filtersCorrectly() throws Exception
    {
        when(daoManager.getRecentSearchTerms()).thenReturn(Collections.emptyList());
        activity = mActivityTestRule.launchActivity(startIntent);
        controller = activity.controller;
        Thread.sleep(50); // debounce time - could make this a static variable and set to zero

        when(searchFunction.apply(any())).thenReturn(Observable.just(results));
        assertEquals(controller.getAdapter().getItemCount(), 0);
        onView(withId(R.id.search_src_text)).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText(searchQuery));
        closeSoftKeyboard();
        Thread.sleep(501); // debounce time - could make this a static variable and set to zero
        assertEquals(controller.getAdapter().getItemCount(), results.size());
        onView(withText("Artist")).perform(click());
        onView(withText(results.get(0).getTitle())).check(matches(isDisplayed()));
        Thread.sleep(5);
        onView(withText("Master")).perform(click());
        onView(withText(results.get(1).getTitle())).check(matches(isDisplayed()));
        onView(withText(results.get(2).getTitle())).check(matches(isDisplayed()));
        Thread.sleep(5);
        onView(withText("Release")).perform(click());
        onView(withText(results.get(3).getTitle())).check(matches(isDisplayed()));
        onView(withText(results.get(4).getTitle())).check(matches(isDisplayed()));
        onView(withText(results.get(5).getTitle())).check(matches(isDisplayed()));
        Thread.sleep(5);
        onView(withText("Label")).perform(click());
        onView(withText("No search results")).check(matches(isDisplayed()));
    }

    @Test
    public void clickList_intendsCorrectly() throws Exception
    {
        when(daoManager.getRecentSearchTerms()).thenReturn(Collections.emptyList());
        activity = mActivityTestRule.launchActivity(startIntent);
        controller = activity.controller;
        TestUtils.stubIntents(ReleaseActivity.class);
        TestUtils.stubIntents(MasterActivity.class);
        TestUtils.stubIntents(ArtistActivity.class);

        when(searchFunction.apply(any())).thenReturn(Observable.just(results));
        onView(withId(R.id.search_src_text)).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText(searchQuery));
        closeSoftKeyboard();
        Thread.sleep(501); // debounce time - could make this a static variable and set to zero

        assertEquals(controller.getAdapter().getItemCount(), results.size());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(0).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(1).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(2).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(3).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(4).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(5).getTitle())), click()));

        intended(allOf(
                hasComponent(ArtistActivity.class.getName()),
                hasExtra("title", results.get(0).getTitle()),
                hasExtra("id", results.get(0).getId())));
        intended(allOf(
                hasComponent(MasterActivity.class.getName()),
                hasExtra("title", results.get(1).getTitle()),
                hasExtra("id", results.get(1).getId())));
        intended(allOf(
                hasComponent(MasterActivity.class.getName()),
                hasExtra("title", results.get(2).getTitle()),
                hasExtra("id", results.get(2).getId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", results.get(3).getTitle()),
                hasExtra("id", results.get(3).getId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", results.get(4).getTitle()),
                hasExtra("id", results.get(4).getId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", results.get(5).getTitle()),
                hasExtra("id", results.get(5).getId())));
    }
}
