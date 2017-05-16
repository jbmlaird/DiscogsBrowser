package bj.discogsbrowser.singlelist;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.wantlist.BasicInformation;
import bj.discogsbrowser.model.wantlist.Want;

/**
 * Created by Josh Laird on 16/05/2017.
 */

public class WantFactory
{
    public static List<Want> getThreeWants()
    {
        return Arrays.asList(buildWant(), buildWant(), buildWant());
    }

    private static Want buildWant()
    {
        Want want = new Want();
        want.setId("ye");
        want.setNotes("ye");
        want.setRating(12);
        want.setResourceUrl("url");
        want.setSubtitle("subtitleFam");
        want.setBasicInformation(buildBasicInformation());
        return want;
    }

    private static BasicInformation buildBasicInformation()
    {
        BasicInformation basicInformation = new BasicInformation();
        basicInformation.setTitle("title");
        return basicInformation;
    }
}
