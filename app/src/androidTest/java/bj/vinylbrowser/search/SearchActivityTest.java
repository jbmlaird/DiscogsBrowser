//package bj.vinylbrowser.search;
//
//import android.content.Intent;
//import android.support.test.espresso.contrib.RecyclerViewActions;
//import android.support.test.espresso.intent.rule.IntentsTestRule;
//import android.support.test.filters.MediumTest;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import bj.vinylbrowser.R;
//import bj.vinylbrowser.artist.ArtistActivity;
//import bj.vinylbrowser.greendao.DaoManager;
//import bj.vinylbrowser.greendao.SearchTerm;
//import bj.vinylbrowser.master.MasterActivity;
//import bj.vinylbrowser.model.search.SearchResult;
//import bj.vinylbrowser.release.ReleaseActivity;
//import bj.vinylbrowser.testutils.EspressoDaggerMockRule;
//import bj.vinylbrowser.testutils.TestUtils;
//import bj.vinylbrowser.utils.ImageViewAnimator;
//import io.reactivex.Observable;
//import io.reactivex.ObservableSource;
//import io.reactivex.functions.Function;
//
//import static android.support.test.espresso.Espresso.closeSoftKeyboard;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.intent.Intents.intended;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
//import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static bj.vinylbrowser.testutils.EspressoDaggerMockRule.getApp;
//import static junit.framework.Assert.assertEquals;
//import static org.hamcrest.Matchers.allOf;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Josh Laird on 16/05/2017.
// * <p>
// * TODO: Real network tests.
// * <p>
// * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
// */
//@MediumTest
//@RunWith(AndroidJUnit4.class)
//public class SearchActivityTest
//{
//    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
//    @Rule
//    public IntentsTestRule<SearchActivity> mActivityTestRule = new IntentsTestRule<>(SearchActivity.class, false, false);
//    @Mock ImageViewAnimator imageViewAnimator;
//    @Mock DaoManager daoManager;
//    @Mock Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchFunction;
//    private List<SearchResult> results = SearchResultFactory.getOneArtistTwoMastersThreeReleases();
//    private SearchActivity activity;
//    private SearchController epxController;
//    private String searchQuery = "yeeeeboi";
//    private Intent startIntent;
//    private String searchTermText = "search term";
//
//    @Before
//    public void setUp()
//    {
//        startIntent = SearchActivity.createIntent(getApp());
//        doAnswer(invocation ->
//                // Disable spinning to not cause Espresso timeout
//                invocation).when(imageViewAnimator).rotateImage(any());
//    }
//
//    @Test
//    public void clickingRecentSearch_fillsBox()
//    {
//        SearchTerm searchTerm = new SearchTerm();
//        searchTerm.setSearchTerm(searchTermText);
//        when(daoManager.getRecentSearchTerms()).thenReturn(Collections.singletonList(searchTerm));
//
//        activity = mActivityTestRule.launchActivity(startIntent);
//        epxController = activity.epxController;
//
//        onView(withText(searchTermText)).perform(click());
//        onView(withId(R.id.search_src_text)).check(new TestUtils.isEditTextEqualTo(searchTerm.getSearchTerm()));
//        assertEquals(epxController.getAdapter().getItemCount(), 1);
//    }
//
//    @Test
//    public void filtersCorrectly() throws Exception
//    {
//        when(daoManager.getRecentSearchTerms()).thenReturn(Collections.emptyList());
//        activity = mActivityTestRule.launchActivity(startIntent);
//        epxController = activity.epxController;
//        Thread.sleep(50); // debounce time - could make this a static variable and set to zero
//
//        when(searchFunction.apply(any())).thenReturn(Observable.just(results));
//        assertEquals(epxController.getAdapter().getItemCount(), 0);
//        onView(withId(R.id.search_src_text)).perform(click());
//        onView(withId(R.id.search_src_text)).perform(typeText(searchQuery));
//        closeSoftKeyboard();
//        Thread.sleep(501); // debounce time - could make this a static variable and set to zero
//        assertEquals(epxController.getAdapter().getItemCount(), results.size());
//        onView(withText("Artist")).perform(click());
//        onView(withText(results.get(0).getTitle())).check(matches(isDisplayed()));
//        Thread.sleep(500);
//        onView(withText("Master")).perform(click());
//        onView(withText(results.get(1).getTitle())).check(matches(isDisplayed()));
//        onView(withText(results.get(2).getTitle())).check(matches(isDisplayed()));
//        Thread.sleep(500);
//        onView(withText("Release")).perform(click());
//        onView(withText(results.get(3).getTitle())).check(matches(isDisplayed()));
//        onView(withText(results.get(4).getTitle())).check(matches(isDisplayed()));
//        onView(withText(results.get(5).getTitle())).check(matches(isDisplayed()));
//        Thread.sleep(500);
//        onView(withText("Label")).perform(click());
//        onView(withText("No search results")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void clickList_intendsCorrectly() throws Exception
//    {
//        when(daoManager.getRecentSearchTerms()).thenReturn(Collections.emptyList());
//        activity = mActivityTestRule.launchActivity(startIntent);
//        epxController = activity.epxController;
//        TestUtils.stubIntentClass(ReleaseActivity.class);
//        TestUtils.stubIntentClass(MasterActivity.class);
//        TestUtils.stubIntentClass(ArtistActivity.class);
//
//        when(searchFunction.apply(any())).thenReturn(Observable.just(results).delay(500, TimeUnit.MILLISECONDS)); // Delay to stop setLoading() and setResults() at the same time
//        onView(withId(R.id.search_src_text)).perform(click());
//        onView(withId(R.id.search_src_text)).perform(typeText(searchQuery));
//        closeSoftKeyboard();
//        Thread.sleep(1501); // debounce time - could make this a static variable and set to zero
//        assertEquals(epxController.getAdapter().getItemCount(), results.size());
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(0).getTitle())), click()));
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(1).getTitle())), click()));
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(2).getTitle())), click()));
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(3).getTitle())), click()));
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(4).getTitle())), click()));
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(results.get(5).getTitle())), click()));
//
//        intended(allOf(
//                hasComponent(ArtistActivity.class.getName()),
//                hasExtra("title", results.get(0).getTitle()),
//                hasExtra("id", results.get(0).getId())));
//        intended(allOf(
//                hasComponent(MasterActivity.class.getName()),
//                hasExtra("title", results.get(1).getTitle()),
//                hasExtra("id", results.get(1).getId())));
//        intended(allOf(
//                hasComponent(MasterActivity.class.getName()),
//                hasExtra("title", results.get(2).getTitle()),
//                hasExtra("id", results.get(2).getId())));
//        intended(allOf(
//                hasComponent(ReleaseActivity.class.getName()),
//                hasExtra("title", results.get(3).getTitle()),
//                hasExtra("id", results.get(3).getId())));
//        intended(allOf(
//                hasComponent(ReleaseActivity.class.getName()),
//                hasExtra("title", results.get(4).getTitle()),
//                hasExtra("id", results.get(4).getId())));
//        intended(allOf(
//                hasComponent(ReleaseActivity.class.getName()),
//                hasExtra("title", results.get(5).getTitle()),
//                hasExtra("id", results.get(5).getId())));
//    }
//}
