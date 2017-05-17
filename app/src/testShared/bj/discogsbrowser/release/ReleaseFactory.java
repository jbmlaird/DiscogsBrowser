package bj.discogsbrowser.release;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.common.Artist;
import bj.discogsbrowser.model.listing.ScrapeListing;
import bj.discogsbrowser.model.release.Image;
import bj.discogsbrowser.model.release.Label;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.model.release.Track;
import bj.discogsbrowser.model.release.Video;

/**
 * Created by Josh Laird on 16/05/2017.
 */

public class ReleaseFactory
{
    public static Release buildRelease(String releaseNumber, boolean inCollection, boolean inWantlist)
    {
        Release release = new Release();
        release.setId("releaseId" + releaseNumber);
        release.setTitle("releaseTitle" + releaseNumber);
        release.setTracklist(Arrays.asList(buildTrack("track1", "A1"), buildTrack("track2", "A2")));
        release.setArtists(Collections.singletonList(buildArtist("releaseArtist" + releaseNumber)));
        release.setImages(Collections.singletonList(new Image()));
        release.setInstanceId("");
        release.setIsInCollection(inCollection);
        release.setIsInWantlist(inWantlist);
        release.setNumForSale(0);
        release.setLowestPriceString("");
        release.setThumb("");
        release.setLabels(Collections.singletonList(LabelFactory.buildLabel()));
        release.setVideos(Collections.singletonList(buildVideo("videoSonnnn")));
        return release;
    }

    private static Artist buildArtist(String artistName)
    {
        Artist artist = new Artist();
        artist.setName(artistName);
        return artist;
    }

    private static Track buildTrack(String trackName, String trackNumber)
    {
        Track track = new Track();
        track.setTitle(trackName);
        track.setPosition(trackNumber);
        return track;
    }

    private static Video buildVideo(String title)
    {
        Video video = new Video();
        video.setTitle(title);
        video.setUri("yeeee=yee");
        return video;
    }

    public static List<ScrapeListing> getReleaseListings()
    {
        return Arrays.asList(buildScrapeListing("50", "5.00", "Good", "Great", "Joy Orbison",
                "50", "United Kingdom", "2"), buildScrapeListing("70", "7.00", "Superb", "Pretty sweet", "Boddika",
                "57", "Landan", "3"));
    }

    private static ScrapeListing buildScrapeListing(String price, String convertedPrice, String mediaCondition,
                                                    String sleeveCondition, String sellerName,
                                                    String sellerRating, String shipsFrom, String marketPlaceId)
    {
        return new ScrapeListing(price, convertedPrice, mediaCondition, sleeveCondition,
                "", sellerName, sellerRating, shipsFrom, marketPlaceId);
    }

    public static Release getReleaseNoLabelNoneForSaleNoTracklistNoVideos()
    {
        Release release = new Release();
        release.setNumForSale(0);
        release.setLabels(Collections.emptyList());
        return release;
    }

    public static bj.discogsbrowser.model.label.Label getLabelDetails()
    {
        bj.discogsbrowser.model.label.Label label = new bj.discogsbrowser.model.label.Label();
        Image image = new Image();
        image.setUri("yeee");
        label.setImages(java.util.Collections.singletonList(image));
        return label;
    }

    public static Release getReleaseWithLabelNoneForSale()
    {
        Release release = new Release();
        release.setNumForSale(0);
        release.setLabels(Arrays.asList(getLabel()));
        return release;
    }

    private static Label getLabel()
    {
        Label label = new Label();
        label.setId("123");
        label.setName("yeson");
        label.setThumb("yeson");
        label.setCatno("yeson");
        return label;
    }

    public static Release getReleaseWithLabelSomeForSale()
    {
        Release release = new Release();
        release.setNumForSale(1);
        release.setLabels(Arrays.asList(getLabel()));
        return release;
    }

    public static Release getReleaseWithLabelSevenTracksNoVideos()
    {
        Release release = new Release();
        release.setLabels(Arrays.asList(getLabel()));
        release.setTracklist(Arrays.asList(getTrack(), getTrack(), getTrack(), getTrack(), getTrack(), getTrack(), getTrack()));
        return release;
    }

    public static Release getReleaseWithLabelFiveTracksTwoVideos()
    {
        Release release = new Release();
        release.setLabels(Arrays.asList(getLabel()));
        release.setVideos(Arrays.asList(getVideo(), getVideo()));
        release.setTracklist(Arrays.asList(getTrack(), getTrack(), getTrack(), getTrack(), getTrack(), getTrack()));
        return release;
    }

    private static Video getVideo()
    {
        Video video = new Video();
        video.setUri("ye=ye");
        return video;
    }

    public static Track getTrack()
    {
        Track track = new Track();
        track.setTitle("ye son");
        track.setPosition("errr");
        return track;
    }

    public static List<ScrapeListing> getOneEmptyScrapeListing()
    {
        ScrapeListing[] scrapeListings = {new ScrapeListing("", "", "", "", "", "", "", "", "")};
        return Arrays.asList(scrapeListings);
    }

    public static List<ScrapeListing> getFourEmptyScrapeListings()
    {
        ScrapeListing[] scrapeListings = {new ScrapeListing("", "", "", "", "", "", "", "", ""),
                new ScrapeListing("", "", "", "", "", "", "", "", ""), new ScrapeListing("", "", "", "", "", "", "", "", ""), new ScrapeListing("", "", "", "", "", "", "", "", "")};
        return Arrays.asList(scrapeListings);
    }
}
