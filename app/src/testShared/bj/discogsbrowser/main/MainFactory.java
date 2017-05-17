package bj.discogsbrowser.main;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.greendao.ViewedRelease;

/**
 * Created by Josh Laird on 16/05/2017.
 */
public class MainFactory
{
    public static List<ViewedRelease> buildFourViewedReleases()
    {
        return Arrays.asList(buildViewedRelease("1"), buildViewedRelease("2"),
                buildViewedRelease("3"), buildViewedRelease("4"));
    }

    public static ViewedRelease buildViewedRelease(String number)
    {
        ViewedRelease viewedRelease = new ViewedRelease();
        viewedRelease.setStyle("techno");
        viewedRelease.setReleaseName("viewedReleaseName" + number);
        viewedRelease.setArtists("viewedReleaseArtists" + number);
        viewedRelease.setReleaseId("viewedReleaseId" + number);
        return viewedRelease;
    }
}
