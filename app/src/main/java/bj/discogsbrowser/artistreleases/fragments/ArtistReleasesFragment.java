package bj.discogsbrowser.artistreleases.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.R;
import bj.discogsbrowser.artistreleases.ArtistReleasesActivity;
import bj.discogsbrowser.artistreleases.ArtistReleasesController;
import bj.discogsbrowser.artistreleases.ArtistReleasesPresenter;
import bj.discogsbrowser.common.BaseFragment;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by Josh Laird on 11/04/2017.
 */
public class ArtistReleasesFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @Inject BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    @Inject ArtistReleasesPresenter presenter;
    @Inject ArtistReleasesController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ArtistReleasesActivity.component.inject(this);
        View view = inflater.inflate(R.layout.fragment_artist_releases, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.setupRecyclerView(recyclerView, getActivity());
        presenter.setupFilter(filterConsumer());
        presenter.connectToBehaviorRelay(getArguments().getString("map"));
        return view;
    }

    /**
     * Consumer to filter results.
     * <p>
     * In View because each view will have to filter differently.
     *
     * @return Filter results Consumer.
     */
    private Consumer<CharSequence> filterConsumer()
    {
        return filterText ->
                presenter.setFilterText(filterText);
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
}
