//package bj.vinylbrowser.artistreleases;
//
//import android.content.Intent;
//import android.support.test.espresso.contrib.RecyclerViewActions;
//import android.support.test.espresso.intent.rule.IntentsTestRule;
//import android.support.test.filters.MediumTest;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.jakewharton.rxrelay2.BehaviorRelay;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//
//import java.util.List;
//
//import bj.vinylbrowser.R;
//import bj.vinylbrowser.model.artistrelease.ArtistRelease;
//import bj.vinylbrowser.network.DiscogsInteractor;
//import bj.vinylbrowser.testutils.EspressoDaggerMockRule;
//import bj.vinylbrowser.testutils.RecyclerViewSizeAssertion;
//import bj.vinylbrowser.testutils.TestUtils;
//import bj.vinylbrowser.utils.ImageViewAnimator;
//import io.reactivex.Single;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.clearText;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.swipeLeft;
//import static android.support.test.espresso.action.ViewActions.swipeRight;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.intent.Intents.intending;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static bj.vinylbrowser.testutils.TestUtils.withCustomConstraints;
//import static org.hamcrest.Matchers.allOf;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Josh Laird on 15/05/2017.
// * <p>
// * This doesn't use a mock Presenter as it uses a BehaviorRelay which passes data to the fragments.
// * <p>
// * Due to the Roboletric tests which test the models have been built, these Espresso tests test onClick and content.
// */
//@MediumTest
//@RunWith(AndroidJUnit4.class)
//public class ArtistReleasesMockNetworkTest
//{
//    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
//    @Rule
//    public IntentsTestRule<ArtistReleasesActivity> mActivityTestRule = new IntentsTestRule<>(ArtistReleasesActivity.class, false, false);
//    //    @Mock ArtistReleasesPresenter presenter;
//    @Mock DiscogsInteractor discogsInteractor;
//    @Mock ImageViewAnimator imageViewAnimator;
//    @Mock ArtistReleaseBehaviorRelay behaviorRelay;
//    private ArtistReleasesActivity activity;
//    private String artistId = "2089744";
//    private String artistTitle = "artistTitle";
//
//    @Before
//    public void setUp() throws InterruptedException
//    {
//        Intent startingIntent = ArtistReleasesActivity.createIntent(EspressoDaggerMockRule.getApp(), artistTitle, artistId);
//        BehaviorRelay<List<ArtistRelease>> artistReleaseRelay = BehaviorRelay.create();
//
//        doAnswer(invocation ->
//                // Disable spinning to not cause Espresso timeout
//                invocation).when(imageViewAnimator).rotateImage(any());
//        when(discogsInteractor.fetchArtistsReleases(artistId)).thenReturn(Single.just(ArtistReleasesFactory.getTwoMastersTwoReleases()));
//        when(behaviorRelay.getArtistReleaseBehaviorRelay()).thenReturn(artistReleaseRelay);
//
//        activity = mActivityTestRule.launchActivity(startingIntent);
//    }
//
//    @Test
//    public void loadData_viewPagerContainsCorrectData()
//    {
//        onView(withText("master1")).check(matches(isDisplayed()));
//        onView(withText("master2")).check(matches(isDisplayed()));
//
//        onView(withText("Releases")).perform(click());
//        onView(withText("release1")).check(matches(isDisplayed()));
//        onView(withText("release2")).check(matches(isDisplayed()));
//
//        onView(withId(R.id.viewpager)).perform(withCustomConstraints(swipeRight(), isDisplayingAtLeast(80)));
//        onView(withText("master1")).check(matches(isDisplayed()));
//        onView(withText("master2")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void filterBoth_filtersCorrectly()
//    {
//        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(new RecyclerViewSizeAssertion(4)); // 4 as they will always have a SmallEmptySpaceModel_ top and bottom
//        onView(withId(R.id.etFilter)).perform(typeText("master1"));
//        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(new RecyclerViewSizeAssertion(3));
//        onView(withId(R.id.etFilter)).perform(typeText("1"));
//        onView(allOf(withText(activity.getString(R.string.no_items)), isDisplayed())).check(matches(isDisplayed()));
//
//        onView(withId(R.id.viewpager)).perform(withCustomConstraints(swipeLeft(), isDisplayingAtLeast(50)));
//
//        onView(allOf(withText(activity.getString(R.string.no_items)), isDisplayed())).check(matches(isDisplayed()));
//        onView(withId(R.id.etFilter)).perform(clearText());
//        onView(allOf(withId(R.id.recyclerView), isDisplayingAtLeast(51))).check(new RecyclerViewSizeAssertion(4));
//    }
//
//    @Test
//    public void onClick_launchesCorrectIntents()
//    {
//        TestUtils.stubIntentClass(ReleaseActivity.class);
//        TestUtils.stubIntentClass(MasterActivity.class);
//
//        // Start from 1 as first element is SmallEmptySpaceModel_
//        onView(allOf(withId(R.id.recyclerView), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
//        onView(allOf(withId(R.id.recyclerView), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
//
//        onView(withId(R.id.viewpager)).perform(withCustomConstraints(swipeLeft(), isDisplayingAtLeast(51)));
//
//        onView(allOf(withId(R.id.recyclerView), isDisplayingAtLeast(51))).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
//        onView(allOf(withId(R.id.recyclerView), isDisplayingAtLeast(51))).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
//
//        intending(allOf(
//                hasComponent(ReleaseActivity.class.getName()),
//                hasExtra("id", "r1"),
//                hasExtra("title", "release1")));
//
//        intending(allOf(
//                hasComponent(ReleaseActivity.class.getName()),
//                hasExtra("id", "r2"),
//                hasExtra("title", "release2")));
//
//        intending(allOf(
//                hasComponent(MasterActivity.class.getName()),
//                hasExtra("id", "m1"),
//                hasExtra("title", "master1")));
//
//        intending(allOf(
//                hasComponent(MasterActivity.class.getName()),
//                hasExtra("id", "m2"),
//                hasExtra("title", "master2")));
//    }
//}
