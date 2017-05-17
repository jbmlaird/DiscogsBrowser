package bj.discogsbrowser.artistreleases.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.artistreleases.ArtistReleasesActivity;
import bj.discogsbrowser.artistreleases.ArtistReleasesController;
import bj.discogsbrowser.common.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 11/04/2017.
 * <p>
 * Fragment containing a RecyclerView.
 */
public class ArtistReleasesFragment extends BaseFragment implements ArtistReleasesFragmentContract.View
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @Inject ArtistReleasesFragmentPresenter presenter;
    @Inject ArtistReleasesController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_artist_releases, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView(recyclerView, controller);
        presenter.connectToBehaviorRelay(getArguments().getString("map"));
        presenter.bind(this);
        return view;
    }

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        ArtistReleasesActivity.getComponent()
                .artistReleasesFragmentComponentBuilder()
                .artistReleasesFragmentModule(new ArtistReleasesFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        presenter.dispose();
    }

    private void setupRecyclerView(RecyclerView recyclerView, ArtistReleasesController controller)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(controller.getAdapter());
        controller.requestModelBuild();
    }

    @Override
    public ArtistReleasesController getController()
    {
        return controller;
    }
}
