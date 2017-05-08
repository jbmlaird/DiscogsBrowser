package bj.discogsbrowser.testmodels;

import java.util.ArrayList;
import java.util.Collections;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artist.Member;
import bj.discogsbrowser.model.release.Image;
import bj.discogsbrowser.utils.WantedUrl;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class TestArtistResultFactory
{
    public ArtistResult getTestArtistResultNoMembers()
    {
        ArtistResult artistResult = new ArtistResult();
        artistResult.setProfile("");
        Image image = new Image();
        image.setResourceUrl("");
        artistResult.setImages(Collections.singletonList(image));
        artistResult.setNamevariations(Collections.singletonList("ye"));
        return artistResult;
    }

    public ArtistResult getTestArtistResultMembers()
    {
        ArtistResult artistResult = new ArtistResult();
        Member member = new Member();
        member.setName("Ye");
        member.setId("Son");
        artistResult.setMembers(Collections.singletonList(member));
        return artistResult;
    }

    public ArtistResult getTestArtistResultReleasesUrl()
    {
        ArtistResult artistResult = new ArtistResult();
        artistResult.setReleasesUrl("");
        return artistResult;
    }

    public ArtistResult getTestArtistResultWantedUrls()
    {
        ArtistResult artistResult = new ArtistResult();
        ArrayList<WantedUrl> wantedUrls = new ArrayList<>();
        wantedUrls.add(new WantedUrl("", "", "", ""));
        artistResult.setWantedUrls(wantedUrls);
        return artistResult;
    }
}
