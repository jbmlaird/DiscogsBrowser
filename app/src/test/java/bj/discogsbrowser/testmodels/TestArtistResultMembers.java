package bj.discogsbrowser.testmodels;

import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artist.Member;

/**
 * Created by Josh Laird on 04/05/2017.
 */

public class TestArtistResultMembers extends ArtistResult
{
    @Override
    public String getId()
    {
        return "bj";
    }

    @Override
    public List<Member> getMembers()
    {
        return Collections.singletonList(new TestMember());
    }

    @Override
    public String getProfile()
    {
        return super.getProfile();
    }

}
