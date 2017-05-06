package bj.discogsbrowser.testmodels;

import bj.discogsbrowser.model.artist.Member;

/**
 * Created by Josh Laird on 04/05/2017.
 */

public class TestMember extends Member
{
    @Override
    public String getName()
    {
        return "ye";
    }

    @Override
    public String getId()
    {
        return "bj";
    }
}
