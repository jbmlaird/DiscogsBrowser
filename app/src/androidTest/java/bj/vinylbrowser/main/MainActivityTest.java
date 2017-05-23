package bj.vinylbrowser.main;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

import bj.vinylbrowser.R;
import bj.vinylbrowser.about.AboutActivity;
import bj.vinylbrowser.customviews.Carousel;
import bj.vinylbrowser.greendao.DaoManager;
import bj.vinylbrowser.greendao.ViewedRelease;
import bj.vinylbrowser.listing.ListingFactory;
import bj.vinylbrowser.login.LoginActivity;
import bj.vinylbrowser.marketplace.MarketplaceListingActivity;
import bj.vinylbrowser.model.listing.Listing;
import bj.vinylbrowser.model.order.Order;
import bj.vinylbrowser.model.search.SearchResult;
import bj.vinylbrowser.order.OrderActivity;
import bj.vinylbrowser.order.OrderFactory;
import bj.vinylbrowser.release.ReleaseActivity;
import bj.vinylbrowser.search.SearchActivity;
import bj.vinylbrowser.search.SearchResultFactory;
import bj.vinylbrowser.singlelist.SingleListActivity;
import bj.vinylbrowser.testutils.EspressoDaggerMockRule;
import bj.vinylbrowser.testutils.TestUtils;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.NavigationDrawerBuilder;
import bj.vinylbrowser.utils.SharedPrefsManager;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static bj.vinylbrowser.testutils.EspressoDaggerMockRule.getApp;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 12/05/2017.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class, false, false);
    @Mock MainPresenter presenter;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock SharedPrefsManager sharedPrefsManager;
    private MainActivity activity;
    private NavigationDrawerBuilder navigationDrawerBuilder;
    private String numCollection = "50";
    private String numWantlist = "90";
    private MainController controller;
    private List<ViewedRelease> fourViewedReleases = ViewedReleaseFactory.buildViewedReleases(4);
    private List<SearchResult> recommendations = SearchResultFactory.getThreeReleases();
    private List<Order> orders = OrderFactory.buildListOfOrders(2);
    private List<Listing> listings = ListingFactory.buildNumberOfListings(3);
    @Mock DaoManager daoManager;

    @Before
    public void setUp()
    {
        Intent startingIntent = MainActivity.createIntent(getApp());
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        when(sharedPrefsManager.isUserLoggedIn()).thenReturn(true);
        doAnswer(invocation ->
                // Swallow
                invocation).when(presenter).connectAndBuildNavigationDrawer(any());
        doAnswer(invocation ->
                // Swallow
                invocation).when(presenter).buildRecommendations();
        doAnswer(invocation ->
                // Swallow
                invocation).when(presenter).buildViewedReleases();
        activity = mActivityTestRule.launchActivity(startingIntent);
        controller = activity.controller;
        navigationDrawerBuilder = new NavigationDrawerBuilder(getApp(), sharedPrefsManager, daoManager);
        initialiseUi();
    }

    @Test
    public void navDrawerPressed_intendsCorrectly() throws InterruptedException
    {
        TestUtils.stubIntentClass(SingleListActivity.class);
        TestUtils.stubIntentClass(SearchActivity.class);
        TestUtils.stubIntentClass(LoginActivity.class);
        TestUtils.stubIntentClass(AboutActivity.class);

        onView(withText(numCollection)).check(matches(isDisplayed()));
        onView(withText(numWantlist)).check(matches(isDisplayed()));
        onView(allOf(withText("Collection"), withResourceName("material_drawer_name"))).perform(click());
        onView(TestUtils.getHomeButton()).perform(click()); // Drawer closes on button press
        onView(allOf(withText("Wantlist"), withResourceName("material_drawer_name"))).perform(click());
        onView(TestUtils.getHomeButton()).perform(click());
        onView(allOf(withText("Marketplace"), withResourceName("material_drawer_name"))).perform(click());
        onView(allOf(withText("Selling"), withResourceName("material_drawer_name"))).perform(click());
        onView(TestUtils.getHomeButton()).perform(click());
        onView(allOf(withText("Orders"), withResourceName("material_drawer_name"))).perform(click());
        onView(TestUtils.getHomeButton()).perform(click());
        onView(allOf(withText("Search"), withResourceName("material_drawer_name"))).perform(click());
        onView(TestUtils.getHomeButton()).perform(click());
        onView(allOf(withText("About"), withResourceName("material_drawer_name"))).perform(click());
        onView(TestUtils.getHomeButton()).perform(click());
        onView(allOf(withText("Logout"), withResourceName("material_drawer_name"))).perform(click());

        verify(daoManager).clearRecentSearchTerms();
        verify(daoManager).clearViewedReleases();
        intended(hasComponent(SingleListActivity.class.getName()), times(4));
        intended(hasComponent(SearchActivity.class.getName()));
        intended(hasComponent(AboutActivity.class.getName()));
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void viewedReleases_intendCorrectly() throws InterruptedException
    {
        TestUtils.stubIntentClass(ReleaseActivity.class);
        // Close navdrawer
        onView(withId(R.id.lytMainContent)).perform(swipeLeft());

        controller.setViewedReleases(fourViewedReleases);
        Thread.sleep(500);
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(fourViewedReleases.get(0).getArtists() + " - " + fourViewedReleases.get(0).getReleaseName()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(fourViewedReleases.get(1).getArtists() + " - " + fourViewedReleases.get(1).getReleaseName()))))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(fourViewedReleases.get(1).getArtists() + " - " + fourViewedReleases.get(1).getReleaseName()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(fourViewedReleases.get(1).getArtists() + " - " + fourViewedReleases.get(1).getReleaseName())))) //Use an already displayed view to match the Carousel
                .perform(RecyclerViewActions.scrollToPosition(2));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(fourViewedReleases.get(2).getArtists() + " - " + fourViewedReleases.get(2).getReleaseName()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(fourViewedReleases.get(2).getArtists() + " - " + fourViewedReleases.get(2).getReleaseName())))) //Use an already displayed view to match the Carousel
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(fourViewedReleases.get(3).getArtists() + " - " + fourViewedReleases.get(3).getReleaseName()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", fourViewedReleases.get(0).getReleaseName()),
                hasExtra("id", fourViewedReleases.get(0).getReleaseId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", fourViewedReleases.get(1).getReleaseName()),
                hasExtra("id", fourViewedReleases.get(1).getReleaseId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", fourViewedReleases.get(2).getReleaseName()),
                hasExtra("id", fourViewedReleases.get(2).getReleaseId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", fourViewedReleases.get(3).getReleaseName()),
                hasExtra("id", fourViewedReleases.get(3).getReleaseId())));
    }

    @Test
    public void recommendations_intendCorrectly() throws InterruptedException
    {
        TestUtils.stubIntentClass(ReleaseActivity.class);
        // Close navdrawer
        onView(withId(R.id.lytMainContent)).perform(swipeLeft());
        controller.setRecommendations(recommendations);
        Thread.sleep(500);

        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(recommendations.get(0).getTitle()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(recommendations.get(1).getTitle()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(withClassName(is(Carousel.class.getName())),
                hasDescendant(withText(recommendations.get(2).getTitle()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", recommendations.get(0).getTitle()),
                hasExtra("id", recommendations.get(0).getId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", recommendations.get(1).getTitle()),
                hasExtra("id", recommendations.get(1).getId())));
        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("title", recommendations.get(2).getTitle()),
                hasExtra("id", recommendations.get(2).getId())));
    }

    @Test
    public void orders_intendCorrectly() throws InterruptedException
    {
        TestUtils.stubIntentClass(OrderActivity.class);
        // Close navdrawer
        onView(withId(R.id.lytMainContent)).perform(swipeLeft());
        controller.setOrders(orders);
        Thread.sleep(500);

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Buyer: " + orders.get(0).getBuyer().getUsername())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Buyer: " + orders.get(1).getBuyer().getUsername())), click()));
        intended(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", orders.get(0).getId())));
        intended(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", orders.get(1).getId())));
    }

    @Test
    public void listings_intendCorrectly() throws InterruptedException
    {
        TestUtils.stubIntentClass(MarketplaceListingActivity.class);
        // Close navdrawer
        onView(withId(R.id.lytMainContent)).perform(swipeLeft());
        controller.setSelling(listings);
        Thread.sleep(500);
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(listings.get(0).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(listings.get(1).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(listings.get(2).getTitle())), click()));
        intended(allOf(
                hasComponent(MarketplaceListingActivity.class.getName()),
                hasExtra("id", listings.get(0).getId()),
                hasExtra("title", listings.get(0).getTitle())));
        intended(allOf(
                hasComponent(MarketplaceListingActivity.class.getName()),
                hasExtra("id", listings.get(1).getId()),
                hasExtra("title", listings.get(1).getTitle())));
        intended(allOf(
                hasComponent(MarketplaceListingActivity.class.getName()),
                hasExtra("id", listings.get(2).getId()),
                hasExtra("title", listings.get(2).getTitle())));
    }

    @Test
    public void overFiveItems_seeAllDisplayed()
    {

    }

    private void initialiseUi()
    {
        when(sharedPrefsManager.getUsername()).thenReturn("BjLairy");
        when(sharedPrefsManager.getName()).thenReturn("Joshy Boi");
        when(sharedPrefsManager.getAvatarUrl()).thenReturn("http://thissomegoodshit.com");
        when(sharedPrefsManager.getNumCollection()).thenReturn(numCollection);
        when(sharedPrefsManager.getNumWantlist()).thenReturn(numWantlist);
        activity.runOnUiThread(() ->
        {
            activity.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(activity, activity.toolbar));
            activity.showLoading(false);
            activity.setupRecyclerView();
        });
        onView(withId(R.id.search)).perform(click());
        onView(TestUtils.getHomeButton()).perform(click());
    }
}
