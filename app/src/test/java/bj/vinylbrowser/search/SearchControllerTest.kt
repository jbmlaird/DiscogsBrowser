package bj.vinylbrowser.search

import android.content.Context
import android.os.Build.VERSION_CODES.LOLLIPOP
import bj.vinylbrowser.greendao.SearchTerm
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(LOLLIPOP), manifest = Config.NONE)
class SearchControllerTest {
    lateinit var controller: SearchController
    val context: Context = mock()
    val view: SearchContract.View  = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val tracker: AnalyticsTracker = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = SearchController(context, view, imageViewAnimator, tracker)
        controller.requestModelBuild()
    }

    @Test
    fun initialStateNoSearchTerms_displaysNothing() {
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels.size, 0)
    }

    @Test
    fun initialStateTwoSearchTerms_displaysTwoSearchTerms() {
        controller.setPastSearches(listOf(SearchFactory.buildPastSearch(), SearchFactory.buildPastSearch()))
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "PastSearchModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "PastSearchModel_")
        assertEquals(copyOfModels.size, 2)
    }

    @Test
    fun searchThreeResults_showsResults() {
        controller.searching = true
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 1)

        controller.setSearchResults(SearchFactory.buildThreeSearchResults())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels.size, 3)
    }

    @Test
    fun searchNoResults_showNoResults() {
        controller.searching = true
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 1)

        controller.setSearchResults(emptyList())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "CenterTextModel_")
        assertEquals(copyOfModels.size, 1)
    }

    @Test
    fun errorThenSearch_showResults() {
        controller.setError(true)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels.size, 1)

        controller.searching = true
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 1)

        controller.setSearchResults(SearchFactory.buildThreeSearchResults())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels.size, 3)
    }

    @Test
    fun resultsThenClear_showsPastSearches() {
        controller.setSearchResults(SearchFactory.buildThreeSearchResults())
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SearchResultModel_")
        assertEquals(copyOfModels.size, 3)

        controller.showPastSearches = true
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels.size, 0)

        controller.setPastSearches(Arrays.asList(SearchTerm()))
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "PastSearchModel_")
        assertEquals(copyOfModels.size, 1)
    }
}