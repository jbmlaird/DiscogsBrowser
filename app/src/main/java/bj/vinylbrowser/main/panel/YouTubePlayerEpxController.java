package bj.vinylbrowser.main.panel;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import bj.vinylbrowser.epoxy.main.InfoTextModel_;
import bj.vinylbrowser.epoxy.main.MainTitleModel_;
import bj.vinylbrowser.epoxy.main.YouTubeListModel_;
import bj.vinylbrowser.main.MainPresenter;
import bj.vinylbrowser.model.release.Video;

/**
 * Created by Josh Laird on 01/06/2017.
 */

public class YouTubePlayerEpxController extends EpoxyController
{
    private Context mContext;
    private YouTubePlayerHolder youTubePlayerHolder;
    private MainPresenter mainPresenter;
    List<Video> videos = new ArrayList<>();

    public YouTubePlayerEpxController(Context context, YouTubePlayerHolder youTubePlayerHolder, MainPresenter mainPresenter)
    {
        mContext = context;
        this.youTubePlayerHolder = youTubePlayerHolder;
        this.mainPresenter = mainPresenter;
    }

    @Override
    protected void buildModels()
    {
        new MainTitleModel_()
                .id("Up next model")
                .title("Up next")
                .tvButtonText("Minimise")
                .onClickListener(v -> mainPresenter.minimiseDraggablePanel())
                .size(6)
                .addTo(this);

        if (videos.size() > 0)
            for (Video video : videos)
            {
                try
                {
                    String youtubeId = video.getUri().split("=")[1];
                    new YouTubeListModel_()
                            .onRemoveClick(v ->
                            {
                                videos.remove(video);
                                requestModelBuild();
                            })
                            .onPlayClick(v ->
                            {
                                videos.remove(video);
                                youTubePlayerHolder.youtubePlayer.loadVideo(youtubeId);
                                requestModelBuild();
                            })
                            .imageUrl("https://img.youtube.com/vi/" + youtubeId + "/default.jpg")
                            .context(mContext)
                            .id(youtubeId)
                            .title(video.getTitle())
                            .description(video.getDescription())
                            .addTo(this);
                }
                catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                    // YouTube video returned has invalid URL
                }
            }
        else
            new InfoTextModel_()
                    .id("No up next videos")
                    .infoText("Select videos to play them next")
                    .addTo(this);
    }

    public void addVideo(Video video)
    {
        videos.add(video);
        requestModelBuild();
    }
}
