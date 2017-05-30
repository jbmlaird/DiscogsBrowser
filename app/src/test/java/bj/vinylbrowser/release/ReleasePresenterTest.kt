package bj.vinylbrowser.release

import bj.vinylbrowser.greendao.DaoManager
import bj.vinylbrowser.model.collection.CollectionRelease
import bj.vinylbrowser.model.common.Label
import bj.vinylbrowser.model.listing.ScrapeListing
import bj.vinylbrowser.model.release.Release
import bj.vinylbrowser.model.wantlist.Want
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.ArtistsBeautifier
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class ReleasePresenterTest {
    lateinit var presenter: ReleasePresenter
    val controller: ReleaseEpxController = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    val testScheduler = TestScheduler()
    val daoManager: DaoManager = mock()
    val artistsBeautifier: ArtistsBeautifier = mock()
    val id = "123"

    @Before
    fun setUp() {
        initMocks(this)
        presenter = ReleasePresenter(controller, discogsInteractor,
                TestSchedulerProvider(testScheduler), daoManager, artistsBeautifier)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(controller, discogsInteractor,
                daoManager, artistsBeautifier)
    }

    @Test
    fun getReleaseAndLabelDetailsError_displaysError() {
        whenever(discogsInteractor.fetchReleaseDetails(id)).thenReturn(Single.error<Release>(Throwable()))

        presenter.fetchArtistDetails(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchReleaseDetails(id)
        verify(controller).setReleaseLoading(true)
        verify(controller).setReleaseError(true)
    }

    @Test
    @Throws(IOException::class)
    fun fetchReleaseListingsError_displaysError() {
        whenever(discogsInteractor.getReleaseMarketListings(id)).thenReturn(Single.error<List<ScrapeListing>>(Throwable()))

        presenter.fetchReleaseListings(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).getReleaseMarketListings(id)
        verify(controller).setListingsLoading(true)
        verify(controller).setReleaseListingsError()
    }

    @Test
    @Throws(IOException::class)
    fun checkCollectionError_displaysError() {
        val releaseNoLabelNoneForSale = ReleaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos(id)
        whenever(controller.release).thenReturn(releaseNoLabelNoneForSale)
        whenever(discogsInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.error<List<CollectionRelease>>(Throwable()))

        presenter.checkCollectionWantlist()
        testScheduler.triggerActions()

        verify(controller, times(1)).getRelease()
        verify(discogsInteractor).checkIfInCollection(controller, releaseNoLabelNoneForSale)
        verify(controller).setCollectionWantlistError(true)
    }

    @Test
    @Throws(IOException::class)
    fun checkCollectionValid_displaysValid() {
        val releaseNoLabelNoneForSale = ReleaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos(id)
        whenever(controller.release).thenReturn(releaseNoLabelNoneForSale)
        whenever(discogsInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just<List<CollectionRelease>>(emptyList()))
        whenever(discogsInteractor.checkIfInWantlist(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just<List<Want>>(emptyList()))

        presenter.checkCollectionWantlist()
        testScheduler.triggerActions()

        verify(controller, times(2)).release
        verify(discogsInteractor).checkIfInCollection(controller, releaseNoLabelNoneForSale)
        verify(discogsInteractor).checkIfInWantlist(controller, releaseNoLabelNoneForSale)
        verify(controller).setCollectionWantlistChecked(true)
    }

    @Test
    fun getReleaseNoLabelNoneForSale_displaysRelease() {
        val releaseNoLabelNoneForSale = ReleaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos(id)
        val releaseSingle = Single.just(releaseNoLabelNoneForSale)
        whenever(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle)
        val arrayListArgumentCaptor = ArgumentCaptor.forClass(MutableList::class.java)
        whenever(discogsInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just<List<CollectionRelease>>(emptyList()))
        whenever(discogsInteractor.checkIfInWantlist(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just<List<Want>>(emptyList()))
        whenever(controller.release).thenReturn(releaseNoLabelNoneForSale)

        presenter.fetchArtistDetails(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchReleaseDetails(id)
        verify(controller).setReleaseLoading(true)
        verify(daoManager).storeViewedRelease(releaseNoLabelNoneForSale, artistsBeautifier)
        verify(controller).release = releaseNoLabelNoneForSale
        verify(controller).setReleaseListings(capture(arrayListArgumentCaptor) as MutableList<ScrapeListing>?)
        assertEquals(arrayListArgumentCaptor.value.size, 0)
        verify(discogsInteractor, times(1)).checkIfInCollection(controller, releaseNoLabelNoneForSale)
        verify(discogsInteractor, times(1)).checkIfInWantlist(controller, releaseNoLabelNoneForSale)
        verify(controller, times(2)).release
        verify(controller).setCollectionWantlistChecked(true)
    }

    @Test
    fun getReleaseWithLabelNoneForSale_displaysRelease() {
        val releaseWithLabelNoneForSale = ReleaseFactory.buildReleaseWithLabelNoneForSale(id)
        val releaseSingle = Single.just(releaseWithLabelNoneForSale)
        whenever(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle)
        whenever(discogsInteractor.fetchLabelDetails(any<String>())).thenReturn(Single.just(Label())) //TODO: Change from any(). Java/Kotlin interaction is causing invalid data being returned
        val arrayListArgumentCaptor = ArgumentCaptor.forClass(MutableList::class.java)
        whenever(discogsInteractor.checkIfInCollection(controller, releaseWithLabelNoneForSale)).thenReturn(Single.just<List<CollectionRelease>>(emptyList()))
        whenever(discogsInteractor.checkIfInWantlist(controller, releaseWithLabelNoneForSale)).thenReturn(Single.just<List<Want>>(emptyList()))
        whenever(controller.release).thenReturn(releaseWithLabelNoneForSale)

        presenter.fetchArtistDetails(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchReleaseDetails(id)
        verify(controller).setReleaseLoading(true)
        verify(discogsInteractor).fetchLabelDetails(any<String>()) //TODO: See above
        verify(daoManager).storeViewedRelease(releaseWithLabelNoneForSale, artistsBeautifier)
        verify(controller).release = releaseWithLabelNoneForSale
        verify(controller).setReleaseListings(arrayListArgumentCaptor.capture() as MutableList<ScrapeListing>?)
        assertEquals(arrayListArgumentCaptor.value.size, 0)
        verify(discogsInteractor, times(1)).checkIfInCollection(controller, releaseWithLabelNoneForSale)
        verify(discogsInteractor, times(1)).checkIfInWantlist(controller, releaseWithLabelNoneForSale)
        verify(controller, times(2)).release
        verify(controller).setCollectionWantlistChecked(true)
    }

    @Test
    @Throws(IOException::class)
    fun getReleaseWithLabelAndListings_displaysReleaseAndListings() {
        val releaseWithLabelSomeForSale = ReleaseFactory.buildReleaseWithLabelSomeForSale(id)
        val releaseSingle = Single.just(releaseWithLabelSomeForSale)
        whenever(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle)
        whenever(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.just(Label()))
        val listArgumentCaptor = ArgumentCaptor.forClass(MutableList::class.java)
        whenever(discogsInteractor.getReleaseMarketListings(id)).thenReturn(Single.just<List<ScrapeListing>>(ScrapeListFactory.buildFourEmptyScrapeListing()))

        whenever(discogsInteractor.checkIfInCollection(controller, releaseWithLabelSomeForSale)).thenReturn(Single.just<List<CollectionRelease>>(emptyList()))
        whenever(discogsInteractor.checkIfInWantlist(controller, releaseWithLabelSomeForSale)).thenReturn(Single.just<List<Want>>(emptyList()))
        whenever(controller.release).thenReturn(releaseWithLabelSomeForSale)

        presenter.fetchArtistDetails(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchReleaseDetails(id)
        verify(controller).setReleaseLoading(true)
        verify(discogsInteractor).fetchLabelDetails(id)
        verify(daoManager).storeViewedRelease(releaseWithLabelSomeForSale, artistsBeautifier)
        verify(controller).release = releaseWithLabelSomeForSale
        verify(controller).setReleaseListings(listArgumentCaptor.capture() as MutableList<ScrapeListing>?)
        assertEquals(listArgumentCaptor.value.size, 4)
        verify(discogsInteractor, times(1)).checkIfInCollection(controller, releaseWithLabelSomeForSale)
        verify(discogsInteractor, times(1)).checkIfInWantlist(controller, releaseWithLabelSomeForSale)
        verify(discogsInteractor).getReleaseMarketListings(id)
        verify(controller, times(2)).release
        verify(controller).setCollectionWantlistChecked(true)
        verify(controller).setListingsLoading(true)
    }
}