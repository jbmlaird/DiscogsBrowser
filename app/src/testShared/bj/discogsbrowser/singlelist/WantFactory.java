package bj.discogsbrowser.singlelist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.artist.ArtistFactory;
import bj.discogsbrowser.model.common.BasicInformation;
import bj.discogsbrowser.model.wantlist.Want;

/**
 * Created by Josh Laird on 16/05/2017.
 */

public class WantFactory
{
    public static List<Want> getThreeWants()
    {
        return Arrays.asList(buildWant("want1"), buildWant("want2"), buildWant("want3"));
    }

    private static Want buildWant(String title)
    {
        Want want = new Want();
        want.setId("ye");
        want.setNotes("ye");
        want.setRating(12);
        want.setResourceUrl("url");
        want.setSubtitle("subtitleFam");
        want.setBasicInformation(buildBasicInformation(title));
        return want;
    }

    static BasicInformation buildBasicInformation(String title)
    {
        BasicInformation basicInformation = new BasicInformation();
        basicInformation.setTitle(title);
        basicInformation.setArtists(Collections.singletonList(ArtistFactory.buildSingleArtist()));
        return basicInformation;
    }
}
