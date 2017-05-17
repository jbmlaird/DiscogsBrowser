package bj.discogsbrowser.main;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.greendao.ViewedRelease;

/**
 * Created by Josh Laird on 16/05/2017.
 */
public class MainFactory
{
    public static List<ViewedRelease> buildViewedReleases(int number)
    {
        List<ViewedRelease> viewedReleases = new ArrayList<>();
        for (int i = 0; i < number; i++)
        {
            viewedReleases.add(buildViewedRelease(i));
        }
        return viewedReleases;
    }

    public static ViewedRelease buildViewedRelease(int number)
    {
        ViewedRelease viewedRelease = new ViewedRelease();
        viewedRelease.setStyle("techno");
        viewedRelease.setReleaseName("viewedReleaseName" + number);
        viewedRelease.setArtists("viewedReleaseArtists" + number);
        viewedRelease.setReleaseId("viewedReleaseId" + number);
        return viewedRelease;
    }
}
