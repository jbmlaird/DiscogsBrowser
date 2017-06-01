package bj.vinylbrowser.main.panel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import bj.vinylbrowser.R;
import bj.vinylbrowser.main.MainPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 31/05/2017.
 */
public class YouTubeListFragment extends Fragment
{
    @BindView(R.id.rvYouTube) RecyclerView recyclerView;
    @Inject MainPresenter mainPresenter;
    @Inject YouTubePlayerPresenter presenter;
    @Inject YouTubePlayerEpxController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);
        ButterKnife.bind(this, view);
        mainPresenter.bindYouTubePresenter(presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(controller.getAdapter());
        controller.requestModelBuild();
        return view;
    }
}
