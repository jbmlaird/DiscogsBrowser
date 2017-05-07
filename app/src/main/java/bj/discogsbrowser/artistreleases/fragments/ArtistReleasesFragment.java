package bj.discogsbrowser.artistreleases.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.R;
import bj.discogsbrowser.artistreleases.ArtistReleasesActivity;
import bj.discogsbrowser.artistreleases.ArtistReleasesPresenter;
import bj.discogsbrowser.common.BaseFragment;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by Josh Laird on 11/04/2017.
 */
public class ArtistReleasesFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.ivLoading) ImageView ivLoading;
    @BindView(R.id.lytNoItems) LinearLayout lytNoItems;
    @BindView(R.id.lytError) RelativeLayout lytError;
    @Inject BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    @Inject ArtistReleasesPresenter presenter;
    @Inject ImageViewAnimator imageViewAnimator;
    private ArtistReleasesAdapter rvReleasesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ArtistReleasesActivity.component.inject(this);
        View view = inflater.inflate(R.layout.fragment_artist_releases, container, false);
        unbinder = ButterKnife.bind(this, view);
        imageViewAnimator.rotateImage(ivLoading);
        rvReleasesAdapter = presenter.setupRecyclerView(recyclerView, getActivity());
        presenter.setupFilter(filterConsumer());
        presenter.connectToBehaviorRelay(getConsumer(), getThrowableConsumer(), getArguments().getString("map"));
        return view;
    }

    private Consumer<Throwable> getThrowableConsumer()
    {
        return throwable ->
        {
            throwable.printStackTrace();
            lytError.setVisibility(View.VISIBLE);
            ivLoading.clearAnimation();
            ivLoading.setVisibility(View.GONE);
        };
    }

    private Consumer<List<ArtistRelease>> getConsumer()
    {
        return artistReleases ->
        {
            if (artistReleases.size() == 0)
                lytNoItems.setVisibility(View.VISIBLE);
            else
                lytNoItems.setVisibility(View.GONE);
            ivLoading.clearAnimation();
            ivLoading.setVisibility(View.GONE);
            rvReleasesAdapter.setReleases(artistReleases);
        };
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
}
