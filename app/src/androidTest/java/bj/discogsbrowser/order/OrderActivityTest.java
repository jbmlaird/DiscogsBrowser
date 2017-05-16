package bj.discogsbrowser.order;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.testutils.EspressoDaggerMockRule;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static bj.discogsbrowser.testutils.EspressoDaggerMockRule.getApp;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Josh Laird on 16/05/2017.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class OrderActivityTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public IntentsTestRule<OrderActivity> mActivityTestRule = new IntentsTestRule<>(OrderActivity.class, false, false);
    @Mock OrderPresenter presenter;
    @Mock ImageViewAnimator imageViewAnimator;
    private String orderId = "orderId";
    private OrderActivity activity;
    private OrderController controller;
    private Order order = OrderFactory.getTwoItemsOrder();

    @Before
    public void setUp() throws InterruptedException
    {
        Intent startingIntent = OrderActivity.createIntent(getApp(), orderId);
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(presenter).fetchOrderDetails(orderId);
        activity = mActivityTestRule.launchActivity(startingIntent);
        controller = activity.controller;
    }

    @Test
    public void onLoadTwoItems_initialStateCorrect()
    {
        activity.runOnUiThread(() ->
                controller.setOrderDetails(order));
        onView(withText(orderId)).check(matches(isDisplayed()));
        onView(withText(order.getStatus())).check(matches(isDisplayed()));
        onView(withText(order.getAdditionalInstructions())).check(matches(isDisplayed()));
        onView(withText(order.getItems().get(0).getRelease().getDescription())).check(matches(isDisplayed()));
        onView(withText(order.getItems().get(1).getRelease().getDescription())).check(matches(isDisplayed()));
        onView(withText(order.getBuyer().getUsername())).check(matches(isDisplayed()));
        // TODO: Research how to check for currency signs in Espresso
//        onView(withId(R.id.recyclerView)).perform(
//                RecyclerViewActions.scrollToPosition(controller.getAdapter().getItemCount() - 1));
//        onView(withText("$" + order.getItems().get(0).getPrice().getValue() + "0")).check(matches(isDisplayed())); // dollar due to Android emulator localisation
//        onView(withText("$" + order.getItems().get(1).getPrice().getValue() + "0")).check(matches(isDisplayed())); // dollar due to Android emulator localisation
//        onView(withText("$" + order.getTotal().getValue() + "0")).check(matches(isDisplayed())); // dollar due to Android emulator localisation
    }
}