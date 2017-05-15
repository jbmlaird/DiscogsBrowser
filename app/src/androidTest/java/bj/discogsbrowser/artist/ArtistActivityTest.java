package bj.discogsbrowser.artist;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.thefinestartist.finestwebview.FinestWebViewActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import bj.discogsbrowser.R;
import bj.discogsbrowser.artistreleases.ArtistReleasesActivity;
import bj.discogsbrowser.testutils.EspressoDaggerMockRule;
import bj.discogsbrowser.testutils.TestUtils;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Josh Laird on 15/05/2017.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ArtistActivityTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<ArtistActivity> mActivityTestRule = new IntentsTestRule<>(ArtistActivity.class, false, false);
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock ArtistPresenter presenter;
    private ArtistActivity activity;
    private TestArtistResult artistResult = new TestArtistResult();
    private String artistId = "artistId";
    private String artistTitle = "artistTitle";

    @Before
    public void setUp()
    {
        Intent startingIntent = ArtistActivity.createIntent(EspressoDaggerMockRule.getApp(), artistTitle, artistId);

        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        doAnswer(invocation ->
                // swallow
                invocation).when(presenter).getReleaseAndLabelDetails(artistId);

        activity = mActivityTestRule.launchActivity(startingIntent);
    }

    @Test
    public void onLaunch_allInfoDisplayed()
    {
        activity.controller.setArtist(artistResult);
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(artistResult.getNamevariations().get(0)))));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(artistResult.getMembers().get(0).getName()))));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(artistResult.getMembers().get(1).getName()))));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(artistResult.getWantedUrls().get(0).getDisplayText()))));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(activity.getString(R.string.view_releases)))));
    }

    @Test
    public void membersClick_launchesIntent()
    {
        TestUtils.stubIntents(ArtistActivity.class);

        activity.controller.setArtist(artistResult);

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(artistResult.getMembers().get(0).getName())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(artistResult.getMembers().get(1).getName())), click()));

        intended(allOf(
                hasComponent(ArtistActivity.class.getName()),
                hasExtra("title", artistResult.getMembers().get(0).getName()),
                hasExtra("id", artistResult.getMembers().get(0).getId())));

        intended(allOf(
                hasComponent(ArtistActivity.class.getName()),
                hasExtra("title", artistResult.getMembers().get(1).getName()),
                hasExtra("id", artistResult.getMembers().get(1).getId())));
    }

    @Test
    public void viewReleasesClicked_launchesIntent()
    {
        TestUtils.stubIntents(ArtistReleasesActivity.class);
        activity.controller.setArtist(artistResult);

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(activity.getString(R.string.view_releases))), click()));

        intended(allOf(
                hasComponent(ArtistReleasesActivity.class.getName()),
                hasExtra(equalTo("title"), equalTo(artistResult.getNamevariations().get(0))),
                hasExtra(equalTo("id"), equalTo(artistResult.getId()))));
    }

    @Test
    public void linksClicked_launchesFinestWebView()
    {
        TestUtils.stubIntents(FinestWebViewActivity.class);
        activity.controller.setArtist(artistResult);

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("spotify")), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("redtube")), click()));

        intended(
                hasComponent(FinestWebViewActivity.class.getName()), times(2));
    }
}
