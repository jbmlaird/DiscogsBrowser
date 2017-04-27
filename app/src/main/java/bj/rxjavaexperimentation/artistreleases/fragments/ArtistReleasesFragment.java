package bj.rxjavaexperimentation.artistreleases.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artistreleases.ArtistReleasesActivity;
import bj.rxjavaexperimentation.artistreleases.ArtistReleasesPresenter;
import bj.rxjavaexperimentation.common.BaseFragment;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Josh Laird on 11/04/2017.
 */
public class ArtistReleasesFragment extends BaseFragment
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.ivLoading) ImageView ivLoading;
    @BindView(R.id.lytNoItems) LinearLayout lytNoItems;
    @Inject BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    @Inject ArtistReleasesPresenter presenter;
    @Inject ImageViewAnimator imageViewAnimator;
    private ArtistReleasesAdapter rvReleasesAdapter;
    private List<ArtistRelease> artistReleases = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ArtistReleasesActivity.component.inject(this);
        View view = inflater.inflate(R.layout.fragment_artist_releases, container, false);
        unbinder = ButterKnife.bind(this, view);
        imageViewAnimator.rotateImage(ivLoading);
        presenter.connectToBehaviorRelay(getConsumer(), getArguments().getString("map"));
        rvReleasesAdapter = presenter.setupRecyclerView(recyclerView, getActivity());
        return view;
    }

    private Consumer<List<ArtistRelease>> getConsumer()
    {
        return o ->
        {
            this.artistReleases = o;
            if (o.size() == 0)
                lytNoItems.setVisibility(View.VISIBLE);
            else if (o.get(0).getId().equals("bj"))
                return;
            else
                lytNoItems.setVisibility(View.GONE);
            presenter.setupFilter(filterConsumer());
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
                Observable.fromArray(artistReleases)
                        .flatMapIterable(releases -> releases)
                        .filter(artistRelease ->
                                (artistRelease.getTitle() != null && artistRelease.getTitle().toLowerCase().contains(filterText.toString().toLowerCase())) ||
                                        (artistRelease.getYear() != null && artistRelease.getYear().toLowerCase().contains(filterText.toString().toLowerCase())))
                        .toList()
                        .subscribe(filteredReleases ->
                        {
                            if (filteredReleases.size() == 0)
                                lytNoItems.setVisibility(View.VISIBLE);
                            else
                                lytNoItems.setVisibility(View.GONE);
                            rvReleasesAdapter.setReleases(filteredReleases);
                        });
    }
}
