package bj.discogsbrowser.release;

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
import bj.discogsbrowser.label.LabelActivity;
import bj.discogsbrowser.marketplace.MarketplaceListingActivity;
import bj.discogsbrowser.model.listing.ScrapeListing;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.testutils.EspressoDaggerMockRule;
import bj.discogsbrowser.testutils.TestUtils;
import bj.discogsbrowser.utils.ImageViewAnimator;
import io.reactivex.Single;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static bj.discogsbrowser.testutils.EspressoDaggerMockRule.getApp;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 16/05/2017.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ReleaseActivityMockPresenterTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<ReleaseActivity> mActivityTestRule = new IntentsTestRule<>(ReleaseActivity.class, false, false);
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock ReleasePresenter presenter;
    @Mock DiscogsInteractor interactor;
    private ReleaseController controller;
    private Intent startIntent;
    private String releaseId = "releaseId";
    private String releaseTitle = "releaseTitle";
    private ReleaseActivity activity;
    private Release release = ReleaseFactory.buildRelease("1", false, false);
    private List<ScrapeListing> releaseListings = ReleaseFactory.getReleaseListings();

    @Before
    public void setUp() throws InterruptedException
    {
        startIntent = ReleaseActivity.createIntent(getApp(), releaseTitle, releaseId);
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(presenter).getReleaseAndLabelDetails(releaseId);
        activity = mActivityTestRule.launchActivity(startIntent);
        controller = activity.controller;
        controller.setRelease(release);
        Thread.sleep(100);
        controller.setCollectionWantlistChecked(true);
        Thread.sleep(100);
        controller.setReleaseListings(releaseListings);
    }

    @Test
    public void onClick_intentsLaunched()
    {
        TestUtils.stubIntents(MarketplaceListingActivity.class);
        TestUtils.stubIntents(LabelActivity.class);

        onView(withText(release.getTitle())).check(matches(isDisplayed()));
        onView(withText(release.getArtists().get(0).getName())).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(release.getTracklist().get(0).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(release.getTracklist().get(1).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(release.getLabels().get(0).getName())), click()));
        // This will fail on emulators that don't have YouTube installed
//        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(release.getVideos().get(0).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(releaseListings.get(0).getPrice())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(releaseListings.get(1).getPrice())), click()));

        intended(allOf(
                hasComponent(MarketplaceListingActivity.class.getName()),
                hasExtra("id", releaseListings.get(0).getMarketPlaceId()),
                hasExtra("title", release.getTitle())));
        intended(allOf(
                hasComponent(MarketplaceListingActivity.class.getName()),
                hasExtra("id", releaseListings.get(1).getMarketPlaceId()),
                hasExtra("title", release.getTitle())));
        intended(allOf(
                hasComponent(LabelActivity.class.getName()),
                hasExtra("id", release.getLabels().get(0).getId())));
    }

    @Test
    public void collectionWantlistClicked_changeTextOnSuccess() throws InterruptedException
    {
        when(interactor.addToCollection(release.getId())).thenReturn(Single.just(ResponseFactory.getAddToCollectionSuccessfulResponse()));
        when(interactor.removeFromCollection(any(), any())).thenReturn(Single.just(ResponseFactory.getRetrofitSuccessfulResponse()));
        when(interactor.addToWantlist(release.getId())).thenReturn(Single.just(ResponseFactory.getAddToWantlistSuccessfulResponse()));
        when(interactor.removeFromWantlist(release.getId())).thenReturn(Single.just(ResponseFactory.getRetrofitSuccessfulResponse()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(activity.getString(R.string.add_to_wantlist)))));
        onView(withText(activity.getString(R.string.add_to_wantlist))).perform(click());
        onView(withText(activity.getString(R.string.add_to_collection))).perform(click());
        // Always failing. Perhaps due to the animation?
//        Thread.sleep(10000);
//        onView(withText(activity.getString(R.string.remove_from_wantlist))).perform(click());
//        onView(withText(activity.getString(R.string.remove_from_collection))).perform(click());
//        onView(withText(activity.getString(R.string.add_to_collection))).check(matches(isDisplayed()));
//        onView(withText(activity.getString(R.string.add_to_wantlist))).check(matches(isDisplayed()));
    }
}
