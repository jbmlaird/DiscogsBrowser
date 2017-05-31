//package bj.vinylbrowser.master;
//
//import android.content.Intent;
//import android.support.test.espresso.intent.rule.IntentsTestRule;
//import android.support.test.filters.MediumTest;
//import android.support.test.runner.AndroidJUnit4;
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
//import bj.vinylbrowser.model.master.Master;
//import bj.vinylbrowser.model.version.MasterVersion;
//import bj.vinylbrowser.release.ReleaseActivity;
//import bj.vinylbrowser.testutils.EspressoDaggerMockRule;
//import bj.vinylbrowser.testutils.TestUtils;
//import bj.vinylbrowser.utils.ImageViewAnimator;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
//import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
//import static android.support.test.espresso.intent.Intents.intended;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
//import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static bj.vinylbrowser.testutils.EspressoDaggerMockRule.getApp;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doAnswer;
//
///**
// * Created by Josh Laird on 15/05/2017.
// * <p>
// * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
// */
//@MediumTest
//@RunWith(AndroidJUnit4.class)
//public class MasterActivityMockPresenterTest
//{
//    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
//    @Rule
//    public IntentsTestRule<MasterActivity> mActivityTestRule = new IntentsTestRule<>(MasterActivity.class, false, false);
//    @Mock MasterPresenter presenter;
//    @Mock ImageViewAnimator imageViewAnimator;
//    private String masterTitle = "masterTitle";
//    private String masterId = "masterId";
//    private MasterActivity activity;
//    private MasterEpxController controller;
//    private Master master = MasterFactory.buildMaster();
//    private List<MasterVersion> masterMasterVersions = MasterVersionsFactory.buildMasterVersions(2);
//
//    @Before
//    public void setUp() throws InterruptedException
//    {
//        Intent startingIntent = MasterActivity.createIntent(getApp(), masterTitle, masterId);
//        doAnswer(invocation ->
//                // Disable spinning to not cause Espresso timeout
//                invocation).when(imageViewAnimator).rotateImage(any());
//        doAnswer(invocation ->
//                // Disable spinning to not cause Espresso timeout
//                invocation).when(presenter).fetchArtistDetails(masterId);
//        activity = mActivityTestRule.launchActivity(startingIntent);
//        epxController = activity.epxController;
//        activity.runOnUiThread(() ->
//        {
//            controller.setMaster(master);
//            try
//            {
//                Thread.sleep(100);
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//            controller.setMasterVersions(masterMasterVersions);
//        });
//    }
//
//    @Test
//    public void onLoad_displaysCorrectData() throws InterruptedException
//    {
//        onView(withId(R.id.recyclerView))
//                // Scroll to divider
//                .perform(scrollToPosition(1));
//        onView(withText(master.getTitle())).check(matches(isDisplayed()));
//        onView(withText(master.getArtists().get(0).getName())).check(matches(isDisplayed()));
//        onView(withId(R.id.recyclerView))
//                // Scroll to end
//                .perform(scrollToPosition(activity.recyclerView.getAdapter().getItemCount() - 1));
//        onView(withText(masterMasterVersions.get(0).getTitle())).check(matches(isDisplayed()));
//        onView(withText(masterMasterVersions.get(1).getTitle())).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void onClick_launchesIntents() throws InterruptedException
//    {
//        TestUtils.stubIntentClass(ReleaseActivity.class);
//        onView(withId(R.id.recyclerView))
//                // Scroll to master version
//                .perform(actionOnItem(hasDescendant(withText(masterMasterVersions.get(0).getTitle())), click()));
//        onView(withId(R.id.recyclerView))
//                // Scroll to master version
//                .perform(actionOnItem(hasDescendant(withText(masterMasterVersions.get(1).getTitle())), click()));
//
//        intended(allOf(
//                hasComponent(ReleaseActivity.class.getName()),
//                hasExtra(equalTo("title"), equalTo(master.getTitle())),
//                hasExtra(equalTo("id"), equalTo(masterMasterVersions.get(0).getId()))));
//
//        intended(allOf(
//                hasComponent(ReleaseActivity.class.getName()),
//                hasExtra(equalTo("title"), equalTo(master.getTitle())),
//                hasExtra(equalTo("id"), equalTo(masterMasterVersions.get(1).getId()))));
//    }
//}
