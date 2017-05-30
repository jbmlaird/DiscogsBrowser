package bj.vinylbrowser.artistreleases.child

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.R
import bj.vinylbrowser.artistreleases.ArtistReleasesController
import bj.vinylbrowser.artistreleases.ArtistReleasesEpxController
import bj.vinylbrowser.customviews.MyRecyclerView
import bj.vinylbrowser.model.artistrelease.ArtistRelease
import com.bluelinelabs.conductor.Controller
import kotlinx.android.synthetic.main.controller_artist_releases_child.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class ArtistReleasesChildController(val map: String) : Controller(), ArtistReleasesChildContract.View {
    @Inject lateinit var presenter: ArtistReleasesChildPresenter
    @Inject lateinit var controller: ArtistReleasesEpxController

    constructor(args: Bundle) : this(args.getString("map"))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        ArtistReleasesController.component!!
                .artistReleasesFragmentComponentBuilder()
                .artistReleasesFragmentModule(ArtistReleasesChildModule(this))
                .build()
                .inject(this)

        val view = inflater.inflate(R.layout.controller_artist_releases_child, container, false)
        setupRecyclerView(view.recyclerView, controller)
        presenter.connectToBehaviorRelay(map)
        presenter.bind(this)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        if (view.recyclerView.adapter == null)
            setupRecyclerView(view.recyclerView, controller)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

    private fun setupRecyclerView(recyclerView: MyRecyclerView, controller: ArtistReleasesEpxController) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = controller.adapter
        controller.requestModelBuild()
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        controller.setItems(savedViewState.getParcelableArrayList<ArtistRelease>("artistReleases")) // Must use this syntax to call requestModelBuild()
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        outState.putParcelableArrayList("artistReleases", controller.items as ArrayList<ArtistRelease>)
        super.onSaveViewState(view, outState)
    }
}