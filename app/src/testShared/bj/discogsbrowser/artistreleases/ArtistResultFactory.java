package bj.discogsbrowser.artistreleases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artist.ArtistWantedUrl;
import bj.discogsbrowser.model.artist.Member;
import bj.discogsbrowser.model.release.Image;

/**
 * Created by Josh Laird on 15/05/2017.
 */
public class ArtistResultFactory
{
    public static ArtistResult buildArtistResult(int numberOfMembers)
    {
        List<String> nameVariationList = Collections.singletonList("ye");
        List<String> stringList = Arrays.asList("http://www.spotify.com", "http://redtube.com");
        List<Member> members = new ArrayList<>();
        for (int i = 0; i <= numberOfMembers - 1; i++)
        {
            members.add(buildTestMember(i));
        }
        List<ArtistWantedUrl> artistWantedUrls = Arrays.asList(new ArtistWantedUrl("http://www.spotify.com", "spotify", "#000000", "i"), new ArtistWantedUrl("http://www.redtube.com", "redtube", "#ffffff", "i"));
        List<Image> images = Arrays.asList(buildImage(), buildImage());
        return new ArtistResult(nameVariationList,
                "artistProfile",
                "http://releasesUrl",
                "http://uri",
                stringList,
                "really good quality, we have the best qualities",
                "123",
                images,
                members,
                artistWantedUrls);
    }

    public static Member buildTestMember(int number)
    {
        Member member = new Member();
        member.setName("member" + number);
        return member;
    }

    public static Image buildImage()
    {
        Image image = new Image();
        image.setHeight(420);
        image.setWidth(420);
        image.setResourceUrl("http://resourceUrl");
        return image;
    }
}
