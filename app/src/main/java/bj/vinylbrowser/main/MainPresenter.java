package bj.vinylbrowser.main;

import bj.vinylbrowser.main.panel.YouTubePlayerPresenter;
import bj.vinylbrowser.model.release.Video;

/**
 * Created by Josh Laird on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private MainContract.View view;
    private YouTubePlayerPresenter youTubePlayerPresenter;

    public MainPresenter(MainContract.View view)
    {
        this.view = view;
    }

    public void addVideo(Video video)
    {
        view.displayDraggablePanel();
        youTubePlayerPresenter.addVideo(video);
    }

    public void bindYouTubePresenter(YouTubePlayerPresenter presenter)
    {
        youTubePlayerPresenter = presenter;
    }

    public void minimiseDraggablePanel()
    {
        view.minimiseDraggablePanel();
    }

    public YouTubePlayerPresenter getYouTubePlayerPresenter()
    {
        return youTubePlayerPresenter;
    }
}
