package bj.vinylbrowser.artistreleases.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.R
import bj.vinylbrowser.artistreleases.ArtistReleasesController
import bj.vinylbrowser.artistreleases.ArtistReleasesEpxController
import bj.vinylbrowser.customviews.MyRecyclerView
import com.bluelinelabs.conductor.Controller
import kotlinx.android.synthetic.main.content_main.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class ArtistReleasesChildController(val map: String) : Controller(), ArtistReleasesFragmentContract.View {
    @Inject lateinit var presenter: ArtistReleasesFragmentPresenter
    @Inject lateinit var controller: ArtistReleasesEpxController

    constructor(args: Bundle) : this(args.getString("map"))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        ArtistReleasesController.component!!
                .artistReleasesFragmentComponentBuilder()
                .artistReleasesFragmentModule(ArtistReleasesFragmentModule(this))
                .build()
                .inject(this)

        val view = inflater.inflate(R.layout.fragment_artist_releases, container, false)
        setupRecyclerView(view.recyclerView, controller)
        presenter.connectToBehaviorRelay(map)
        presenter.bind(this)
        return view
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
}