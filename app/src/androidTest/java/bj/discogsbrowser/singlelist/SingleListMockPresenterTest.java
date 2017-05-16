package bj.discogsbrowser.singlelist;

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

import bj.discogsbrowser.R;
import bj.discogsbrowser.model.collection.CollectionRelease;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.model.wantlist.Want;
import bj.discogsbrowser.order.OrderActivity;
import bj.discogsbrowser.order.OrderFactory;
import bj.discogsbrowser.release.ReleaseActivity;
import bj.discogsbrowser.testutils.EspressoDaggerMockRule;
import bj.discogsbrowser.testutils.TestUtils;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static bj.discogsbrowser.testutils.EspressoDaggerMockRule.getApp;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Josh Laird on 16/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class SingleListMockPresenterTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<SingleListActivity> mActivityTestRule = new IntentsTestRule<>(SingleListActivity.class, false, false);
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock SingleListPresenter presenter;
    @Mock AnalyticsTracker tracker;
    private Integer type = R.string.orders;
    private String username = "dasmebro";
    private SingleListActivity singleListActivity;
    private SingleListController controller;

    @Before
    public void setUp()
    {
        Intent startingIntent = SingleListActivity.createIntent(getApp(), type, username);

        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        doAnswer(invocation ->
                // Swallow
                invocation).when(presenter).getData(type, username);
        doAnswer(invocation ->
                // Swallow
                invocation).when(tracker).send(any(), any(), any(), any(), any());
        singleListActivity = mActivityTestRule.launchActivity(startingIntent);
        controller = singleListActivity.controller;
    }

    @Test
    public void sixOrders_displaysAndIntends()
    {
        TestUtils.stubIntents(OrderActivity.class);
        List<Order> listOfSix = OrderFactory.getListOfSix();
        controller.setItems(listOfSix);

        onView(withText(listOfSix.get(0).getTitle())).check(matches(isDisplayed())).perform(click());
        onView(withText(listOfSix.get(1).getTitle())).check(matches(isDisplayed())).perform(click());
        onView(withText(listOfSix.get(2).getTitle())).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollToPosition(controller.getAdapter().getItemCount() - 1));
        onView(withText(listOfSix.get(3).getTitle())).check(matches(isDisplayed())).perform(click());
        onView(withText(listOfSix.get(4).getTitle())).check(matches(isDisplayed())).perform(click());
        onView(withText(listOfSix.get(5).getTitle())).check(matches(isDisplayed())).perform(click());

        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", listOfSix.get(0).getId())));
        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", listOfSix.get(1).getId())));
        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", listOfSix.get(2).getId())));
        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", listOfSix.get(3).getId())));
        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", listOfSix.get(4).getId())));
        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", listOfSix.get(5).getId())));
    }

    @Test
    public void loadThreeWants_displaysAndIntends()
    {
        TestUtils.stubIntents(ReleaseActivity.class);
        List<Want> threeWants = WantFactory.getThreeWants();
        controller.setItems(threeWants);

        onView(withText(threeWants.get(0).getTitle())).check(matches(isDisplayed()));
        onView(withText(threeWants.get(1).getTitle())).check(matches(isDisplayed()));
        onView(withText(threeWants.get(2).getTitle())).check(matches(isDisplayed()));

        intending(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("id", threeWants.get(0).getId()),
                hasExtra("title", threeWants.get(0).getTitle())));
        intending(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("id", threeWants.get(1).getId()),
                hasExtra("title", threeWants.get(1).getTitle())));
        intending(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("id", threeWants.get(2).getId()),
                hasExtra("title", threeWants.get(2).getTitle())));
    }

    @Test
    public void loadThreeSelling_displaysAndIntends()
    {
        TestUtils.stubIntents(OrderActivity.class);
        List<Listing> threeListings = ListingFactory.getThreeListings();
        controller.setItems(threeListings);

        onView(withText(threeListings.get(0).getTitle())).check(matches(isDisplayed()));
        onView(withText(threeListings.get(1).getTitle())).check(matches(isDisplayed()));
        onView(withText(threeListings.get(2).getTitle())).check(matches(isDisplayed()));

        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", threeListings.get(0).getId())));
        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", threeListings.get(1).getId())));
        intending(allOf(
                hasComponent(OrderActivity.class.getName()),
                hasExtra("id", threeListings.get(2).getId())));
    }

    @Test
    public void loadThreeCollection_displaysAndIntends()
    {
        TestUtils.stubIntents(ReleaseActivity.class);
        List<CollectionRelease> threeCollectionReleases = CollectionFactory.getThreeCollectionReleases();
        controller.setItems(threeCollectionReleases);

        onView(withText(threeCollectionReleases.get(0).getTitle())).check(matches(isDisplayed()));
        onView(withText(threeCollectionReleases.get(1).getTitle())).check(matches(isDisplayed()));
        onView(withText(threeCollectionReleases.get(2).getTitle())).check(matches(isDisplayed()));

        intending(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("id", threeCollectionReleases.get(0).getId()),
                hasExtra("title", threeCollectionReleases.get(0).getTitle())));
        intending(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("id", threeCollectionReleases.get(1).getId()),
                hasExtra("title", threeCollectionReleases.get(1).getTitle())));
        intending(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra("id", threeCollectionReleases.get(2).getId()),
                hasExtra("title", threeCollectionReleases.get(2).getTitle())));
    }
}
