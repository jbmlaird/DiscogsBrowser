package bj.discogsbrowser.master;

import android.content.Intent;
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
import bj.discogsbrowser.model.version.Version;
import bj.discogsbrowser.release.ReleaseActivity;
import bj.discogsbrowser.testutils.EspressoDaggerMockRule;
import bj.discogsbrowser.testutils.TestUtils;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static bj.discogsbrowser.testutils.EspressoDaggerMockRule.getApp;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Josh Laird on 15/05/2017.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class MasterActivityTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<MasterActivity> mActivityTestRule = new IntentsTestRule<>(MasterActivity.class, false, false);
    @Mock MasterPresenter presenter;
    @Mock ImageViewAnimator imageViewAnimator;
    private String masterTitle = "masterTitle";
    private String masterId = "masterId";
    private MasterActivity activity;
    private MasterController controller;
    private TestMaster master = new TestMaster();
    private List<Version> masterVersions = MasterVersionsFactory.getTwoMasterVersions();

    @Before
    public void setUp() throws InterruptedException
    {
        Intent startingIntent = MasterActivity.createIntent(getApp(), masterTitle, masterId);
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(presenter).getReleaseAndLabelDetails(masterId);
        activity = mActivityTestRule.launchActivity(startingIntent);
        controller = activity.controller;
        activity.runOnUiThread(() ->
        {
            controller.setMaster(master);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            controller.setMasterVersions(masterVersions);
        });
    }

    @Test
    public void onLoad_displaysCorrectData() throws InterruptedException
    {
        onView(withId(R.id.recyclerView))
                // Scroll to divider
                .perform(scrollToPosition(1));
        onView(withText(master.getTitle())).check(matches(isDisplayed()));
        onView(withText(master.getArtists().get(0).getName())).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView))
                // Scroll to end
                .perform(scrollToPosition(activity.recyclerView.getAdapter().getItemCount() - 1));
        onView(withText(masterVersions.get(0).getTitle())).check(matches(isDisplayed()));
        onView(withText(masterVersions.get(1).getTitle())).check(matches(isDisplayed()));
    }

    @Test
    public void onClick_launchesIntents() throws InterruptedException
    {
        TestUtils.stubIntents(ReleaseActivity.class);
        onView(withId(R.id.recyclerView))
                // Scroll to master version
                .perform(actionOnItem(hasDescendant(withText(masterVersions.get(0).getTitle())), click()));
        onView(withId(R.id.recyclerView))
                // Scroll to master version
                .perform(actionOnItem(hasDescendant(withText(masterVersions.get(1).getTitle())), click()));

        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra(equalTo("title"), equalTo(master.getTitle())),
                hasExtra(equalTo("id"), equalTo(masterVersions.get(0).getId()))));

        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra(equalTo("title"), equalTo(master.getTitle())),
                hasExtra(equalTo("id"), equalTo(masterVersions.get(1).getId()))));
    }
}
