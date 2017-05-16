package bj.discogsbrowser.master;

import java.util.Collections;

import bj.discogsbrowser.model.master.Master;
import bj.discogsbrowser.model.release.Artist;
import bj.discogsbrowser.model.release.Image;

/**
 * Created by Josh Laird on 15/05/2017.
 */

public class TestMaster extends Master
{
    public TestMaster()
    {
        setArtists(Collections.singletonList(new TestArtist()));
        setImages(Collections.singletonList(new TestImage()));
        setTitle("testMasterTitle");
    }

    private class TestArtist extends Artist
    {
        public TestArtist()
        {
            setName("yeeeeheeeh");
        }
    }

    private class TestImage extends Image
    {
        public TestImage()
        {
            setUri("ye");
        }
    }
}
