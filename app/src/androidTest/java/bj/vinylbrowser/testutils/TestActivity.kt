package bj.vinylbrowser.testutils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import bj.vinylbrowser.R
import bj.vinylbrowser.search.SearchController
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        router = Conductor.attachRouter(this, lytFrame, savedInstanceState)
    }

    override fun onBackPressed() {
        if (!router.handleBack())
            super.onBackPressed()
    }

    fun onSearch(menuItem: MenuItem) {
        router.pushController(RouterTransaction.with(SearchController())
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler())
                .tag("searchController"))
    }
}
