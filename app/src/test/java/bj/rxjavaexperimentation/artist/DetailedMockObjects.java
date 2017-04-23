package bj.rxjavaexperimentation.artist;

import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Release;

/**
 * Created by Josh Laird on 19/04/2017.
 */

public class DetailedMockObjects
{
    private Release mockRelease;
    private Label mockLabel;
    private ArtistResult mockArtist;
    private Master mockMaster;

    public DetailedMockObjects()
    {
        mockRelease = new Release();
        mockLabel = new Label();
        mockArtist = new ArtistResult();
        mockMaster = new Master();
    }

    public Release getMockRelease()
    {
        return mockRelease;
    }

    public Label getMockLabel()
    {
        return mockLabel;
    }

    public ArtistResult getMockArtist()
    {
        return mockArtist;
    }

    public Master getMockMaster()
    {
        return mockMaster;
    }
}
