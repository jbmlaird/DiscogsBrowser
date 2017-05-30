package bj.vinylbrowser.label;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Collections;

import bj.vinylbrowser.R;
import bj.vinylbrowser.model.common.Label;
import bj.vinylbrowser.model.labelrelease.LabelRelease;
import bj.vinylbrowser.release.ReleaseActivity;
import bj.vinylbrowser.release.ReleaseFactory;
import bj.vinylbrowser.testutils.EspressoDaggerMockRule;
import bj.vinylbrowser.testutils.TestUtils;
import bj.vinylbrowser.utils.ImageViewAnimator;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Josh Laird on 12/05/2017.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class LabelControllerMockPresenterTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<LabelController> mActivityTestRule = new IntentsTestRule<>(LabelController.class, false, false);
    @Mock LabelPresenter presenter;
    @Mock ImageViewAnimator imageViewAnimator;
    private LabelController activity;
    private Label testLabel;
    private LabelRelease labelRelease;
    private String title = "labelTitle";
    private String id = "labelId";

    @Before
    public void setUp() throws InterruptedException
    {
        labelRelease = LabelFactory.buildLabelRelease(1);
        testLabel = ReleaseFactory.buildReleaseLabel(id);
        Intent startingIntent = LabelController.createIntent(EspressoDaggerMockRule.getApp(), title, id);

        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        doAnswer(invocation ->
                // swallow
                invocation).when(presenter).fetchArtistDetails("id");

        activity = mActivityTestRule.launchActivity(startingIntent);
        activity.controller.setLabel(testLabel);
        Thread.sleep(100); //Sleep as you can't call two requestModelBuilds() simultaneously
        activity.controller.setLabelReleases(Collections.singletonList(labelRelease));
    }

    @Test
    public void onLoad_displaysCorrectData() throws InterruptedException
    {
        onView(withId(R.id.recyclerView))
                // Scroll to bottom as that's where the label info is
                .perform(scrollToPosition(activity.recyclerView.getAdapter().getItemCount() - 1));
        onView(withText(testLabel.getName())).check(matches(isDisplayed()));
        onView(withText(testLabel.getProfile())).check(matches(isDisplayed()));
        onView(withText(labelRelease.getTitle() + " (" + labelRelease.getCatno() + ")")).check(matches(isDisplayed()));
        onView(withText(labelRelease.getArtist())).check(matches(isDisplayed()));
    }

    @Test
    public void onLabelReleaseClicked_launchesReleaseActivity() throws InterruptedException
    {
        TestUtils.stubIntentClass(ReleaseActivity.class);

        onView(withId(R.id.recyclerView))
                // 0: Header, 1: Divider, 2: Release
                .perform(actionOnItemAtPosition(2, click()));

        intended(allOf(
                hasComponent(ReleaseActivity.class.getName()),
                hasExtra(equalTo("title"), equalTo(labelRelease.getTitle())),
                hasExtra(equalTo("id"), equalTo(labelRelease.getId()))));
    }

    @Test
    public void onViewOnDiscogsClicked_launchesWebView() throws InterruptedException
    {
        TestUtils.stubIntentAction(Intent.ACTION_VIEW);

        onView(withId(R.id.recyclerView))
                .perform(actionOnItem(hasDescendant(withText("View on Discogs")), click()));

        intended(hasAction(Intent.ACTION_VIEW));
    }
}
