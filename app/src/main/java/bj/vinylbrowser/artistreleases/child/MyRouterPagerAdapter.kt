package bj.vinylbrowser.artistreleases.child

import android.os.Bundle
import bj.vinylbrowser.common.BaseController
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.support.RouterPagerAdapter

/**
 * Created by Josh Laird on 30/05/2017.
 *
 * TODO: Use a MutableList rather than hard-coding
 */
class MyRouterPagerAdapter(host: BaseController) : RouterPagerAdapter(host) {
    override fun configureRouter(router: Router, position: Int) {
        if (!router.hasRootController()) {
            val bundle: Bundle = Bundle()
            when (position) {
                0 -> bundle.putString("map", "master")
                1 -> bundle.putString("map", "release")
            }
            val artistReleasesChildController = ArtistReleasesChildController(bundle)
            artistReleasesChildController.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            router.setRoot(RouterTransaction.with(artistReleasesChildController))
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Masters"
            1 -> return "Releases"
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return 2
    }
}