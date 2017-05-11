package bj.discogsbrowser.release;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.listing.ScrapeListing;
import bj.discogsbrowser.model.release.Image;
import bj.discogsbrowser.model.release.Label;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.model.release.Track;
import bj.discogsbrowser.model.release.Video;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Created by Josh Laird on 10/05/2017.
 */

public class ReleaseFactory
{
    public Release getReleaseNoLabelNoneForSaleNoTracklistNoVideos()
    {
        Release release = new Release();
        release.setNumForSale(0);
        release.setLabels(Collections.emptyList());
        return release;
    }

    public bj.discogsbrowser.model.label.Label getLabelDetails()
    {
        bj.discogsbrowser.model.label.Label label = new bj.discogsbrowser.model.label.Label();
        Image image = new Image();
        image.setUri("yeee");
        label.setImages(java.util.Collections.singletonList(image));
        return label;
    }

    public Release getReleaseWithLabelNoneForSale()
    {
        Release release = new Release();
        release.setNumForSale(0);
        release.setLabels(Arrays.asList(getLabel()));
        return release;
    }

    private Label getLabel()
    {
        Label label = new Label();
        label.setId("123");
        label.setName("yeson");
        label.setThumb("yeson");
        label.setCatno("yeson");
        return label;
    }

    public Release getReleaseWithLabelSomeForSale()
    {
        Release release = new Release();
        release.setNumForSale(1);
        release.setLabels(Arrays.asList(getLabel()));
        return release;
    }

    public Release getReleaseWithLabelSevenTracksNoVideos()
    {
        Release release = new Release();
        release.setLabels(Arrays.asList(getLabel()));
        release.setTracklist(Arrays.asList(getTrack(), getTrack(), getTrack(), getTrack(), getTrack(), getTrack(), getTrack()));
        return release;
    }

    public Release getReleaseWithLabelFiveTracksTwoVideos()
    {
        Release release = new Release();
        release.setLabels(Arrays.asList(getLabel()));
        release.setVideos(Arrays.asList(getVideo(), getVideo()));
        release.setTracklist(Arrays.asList(getTrack(), getTrack(), getTrack(), getTrack(), getTrack(), getTrack()));
        return release;
    }

    private Video getVideo()
    {
        Video video = new Video();
        video.setUri("ye=ye");
        return video;
    }

    public Track getTrack()
    {
        Track track = new Track();
        track.setTitle("ye son");
        track.setPosition("errr");
        return track;
    }

    public List<ScrapeListing> getOneScrapeListing()
    {
        ScrapeListing[] scrapeListings = {new ScrapeListing("", "", "", "", "", "", "", "", "")};
        return Arrays.asList(scrapeListings);
    }

    public List<ScrapeListing> getFourScrapeListings()
    {
        ScrapeListing[] scrapeListings = {new ScrapeListing("", "", "", "", "", "", "", "", ""),
                new ScrapeListing("", "", "", "", "", "", "", "", ""), new ScrapeListing("", "", "", "", "", "", "", "", ""), new ScrapeListing("", "", "", "", "", "", "", "", "")};
        return Arrays.asList(scrapeListings);
    }
}
