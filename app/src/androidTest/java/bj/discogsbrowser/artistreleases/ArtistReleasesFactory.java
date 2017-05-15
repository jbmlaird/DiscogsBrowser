package bj.discogsbrowser.artistreleases;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;

/**
 * Created by Josh Laird on 15/05/2017.
 */

public class ArtistReleasesFactory
{
    public List<ArtistRelease> getTwoMastersTwoReleases()
    {
        return Arrays.asList(new TestMaster("m1", "master1", "masters1"), new TestMaster("m2", "master2", "masters2"),
                new TestRelease("r1", "release1", "releases1"), new TestRelease("r2", "release2", "releases2"));
    }

    public class TestMaster extends ArtistRelease
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
