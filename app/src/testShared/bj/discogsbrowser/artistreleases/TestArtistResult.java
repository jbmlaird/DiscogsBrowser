package bj.discogsbrowser.artistreleases;

import java.util.Arrays;
import java.util.Collections;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artist.Member;
import bj.discogsbrowser.model.release.Image;
import bj.discogsbrowser.model.artist.ArtistWantedUrl;

/**
 * Created by Josh Laird on 15/05/2017.
 */
public class TestArtistResult extends ArtistResult
{
    public TestArtistResult()
    {
        setNamevariations(Collections.singletonList("ye"));
        setProfile("artistProfile");
        setReleasesUrl("http://releasesUrl");
        setResourceUrl("http://resourceUrl");
        setUri("http://uri");
        setUrls(Arrays.asList("http://www.spotify.com", "http://redtube.com"));
        setDataQuality("really good quality, we have the best qualities");
        setId("123");
        setImages(Arrays.asList(new TestImage(), new TestImage()));
        Member testMember = new Member();
        testMember.setName("member1");
        Member testMember2 = new Member();
        testMember2.setName("member2");
        setMembers(Arrays.asList(testMember, testMember2));
        setArtistWantedUrls(Arrays.asList(new ArtistWantedUrl("http://www.spotify.com", "spotify", "#000000", "i"), new ArtistWantedUrl("http://www.redtube.com", "redtube", "#ffffff", "i")));
    }

    private static class TestImage extends Image
    {
        @Override
        public Integer getHeight()
        {
            return 420;
        }

        @Override
        public String getResourceUrl()
        {
            return "http://resourceUrl";
        }

        @Override
        public String getType()
        {
            return "";
        }

        @Override
        public String getUri()
        {
            return "";
        }

        @Override
        public String getUri150()
        {
            return "";
        }

        @Override
        public Integer getWidth()
        {
            return 420;
        }
    }
}
