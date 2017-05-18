package bj.discogsbrowser.artistreleases;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;

import static bj.discogsbrowser.artistreleases.ArtistResultFactory.buildArtistResult;

/**
 * Created by Josh Laird on 15/05/2017.
 */

public class ArtistReleasesFactory
{
    public static ArtistResult getTestArtistResultNoMembers()
    {
        return buildArtistResult(0);
    }

    public static ArtistResult getTestArtistResultMembers()
    {
        return buildArtistResult(2);
    }

    public static ArtistResult getTestArtistResultReleasesUrl()
    {
        return buildArtistResult(0);
    }

    public static ArtistResult getTestArtistResultWantedUrls()
    {
        return buildArtistResult(0);
    }

    public static List<ArtistRelease> getTwoMastersTwoReleases()
    {
        return Arrays.asList(new TestMaster("m1", "master1", "masters1"), new TestMaster("m2", "master2", "masters2"),
                new TestRelease("r1", "release1", "releases1"), new TestRelease("r2", "release2", "releases2"));
    }

    public static class TestMaster extends ArtistRelease
    {
        public TestMaster(String id, String title, String artist)
        {
            setId(id);
            setTitle(title);
            setArtist(artist);
            setType("master");
        }
    }

    public static class TestRelease extends ArtistRelease
    {
        public TestRelease(String id, String title, String artist)
        {
            setId(id);
            setTitle(title);
            setArtist(artist);
            setType("release");
        }
    }
}
