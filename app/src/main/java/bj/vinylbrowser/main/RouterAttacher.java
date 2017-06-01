package bj.vinylbrowser.main;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import bj.vinylbrowser.home.HomeController;

/**
 * Created by Josh Laird on 01/06/2017.
 * <p>
 * During tests I want to mock this so I don't always attach the HomeController.
 */
public class RouterAttacher
{
    public void attachRoot(Router router)
    {
        HomeController homeController = new HomeController();
        homeController.setRetainViewMode(Controller.RetainViewMode.RETAIN_DETACH);
        if (!router.hasRootController())
        {
            router.setRoot(RouterTransaction.with(homeController)
                    .tag("HomeController"));
        }
    }
}
