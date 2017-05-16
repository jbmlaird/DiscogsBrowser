package bj.discogsbrowser.marketplace;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.thefinestartist.finestwebview.FinestWebViewActivity;

import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import bj.discogsbrowser.R;
import bj.discogsbrowser.testutils.EspressoDaggerMockRule;
import bj.discogsbrowser.testutils.TestUtils;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static bj.discogsbrowser.testutils.EspressoDaggerMockRule.getApp;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Josh Laird on 15/05/2017.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class MarketplaceActivityMockPresenterTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<MarketplaceListingActivity> mActivityTestRule = new IntentsTestRule<>(MarketplaceListingActivity.class, false, false);
    @Mock MarketplacePresenter presenter;
    @Mock ImageViewAnimator imageViewAnimator;
    private MarketplaceListingActivity activity;
    private String listingTitle = "listingTitle";
    private String listingId = "listingId";
    private TestListing listing = new TestListing();
    private TestUserDetails testUserDetails = new TestUserDetails();
    private MarketplaceController controller;

    @Before
    public void setUp() throws InterruptedException
    {
        Intent startingIntent = MarketplaceListingActivity.createIntent(getApp(), listingTitle, listingId, "", "");

        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(presenter).getListingDetails(listingId);

        activity = mActivityTestRule.launchActivity(startingIntent);
        controller = activity.controller;

        activity.runOnUiThread(() ->
        {
            controller.setListing(listing);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            controller.setSellerDetails(testUserDetails);
        });
    }

    @Test
    public void onLoad_displaysCorrectInfo() throws InterruptedException
    {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(listing.getComments()))));
        onView(withText(listing.getCondition())).check(matches(isDisplayed()));
        onView(withText(listing.getSleeveCondition())).check(matches(isDisplayed()));
        onView(withText(listing.getSeller().getUsername())).check(matches(isDisplayed()));
        onView(withText(testUserDetails.getSellerRating() + "%")).check(matches(isDisplayed()));
    }

    @Test
    public void buttonsClicked_intentsLaunched()
    {
        TestUtils.stubIntents(FinestWebViewActivity.class);

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(activity.recyclerView.getAdapter().getItemCount() - 1));

        onView(withText("View seller shipping info")).perform(click());
        onView(withText(listing.getSeller().getUsername()))
                .inRoot(withDecorView(IsNot.not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withText("Dismiss"))
                .inRoot(withDecorView(IsNot.not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).perform(click());
        onView(withText("View on Discogs")).perform(click());
        intended(hasComponent(FinestWebViewActivity.class.getName()));
    }
}
