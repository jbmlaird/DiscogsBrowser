package bj.discogsbrowser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artist.Member;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.model.release.Image;
import bj.discogsbrowser.utils.WantedUrl;

/**
 * Created by Josh Laird on 15/05/2017.
 */

public class ArtistReleasesFactory
{
    public static ArtistResult getTestArtistResultNoMembers()
    {
        ArtistResult artistResult = new ArtistResult();
        artistResult.setProfile("");
        Image image = new Image();
        image.setResourceUrl("");
        artistResult.setImages(Collections.singletonList(image));
        artistResult.setNamevariations(Collections.singletonList("ye"));
        return artistResult;
    }

    public static ArtistResult getTestArtistResultMembers()
    {
        ArtistResult artistResult = new ArtistResult();
        Member member = new Member();
        member.setName("Ye");
        member.setId("Son");
        artistResult.setMembers(Collections.singletonList(member));
        return artistResult;
    }

    public static ArtistResult getTestArtistResultReleasesUrl()
    {
        ArtistResult artistResult = new ArtistResult();
        artistResult.setReleasesUrl("");
        return artistResult;
    }

    public static ArtistResult getTestArtistResultWantedUrls()
    {
        ArtistResult artistResult = new ArtistResult();
        ArrayList<WantedUrl> wantedUrls = new ArrayList<>();
        wantedUrls.add(new WantedUrl("", "", "", ""));
        artistResult.setWantedUrls(wantedUrls);
        return artistResult;
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
