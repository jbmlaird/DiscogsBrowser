package bj.discogsbrowser.label;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.label.Label;
import bj.discogsbrowser.model.labelrelease.LabelRelease;
import bj.discogsbrowser.model.release.Image;

/**
 * Created by Josh Laird on 14/05/2017.
 */
public class TestLabel extends Label
{
    @Override
    public String getProfile()
    {
        return "labelProfile";
    }

    @Override
    public String getReleasesUrl()
    {
        return "releasesUrl";
    }

    @Override
    public String getName()
    {
        return "labelName";
    }

    @Override
    public String getUri()
    {
        return "labelUri";
    }

    @Override
    public List<Image> getImages()
    {
        return Arrays.asList(new TestImage());
    }

    @Override
    public String getResourceUrl()
    {
        return "resourceUrl";
    }

    @Override
    public String getId()
    {
        return "labelId";
    }

    @Override
    public String getDataQuality()
    {
        return "dataquality";
    }

    public class TestImage extends Image
    {
        @Override
        public String getUri()
        {
            return "uri";
        }
    }

    static class TestLabelRelease extends LabelRelease
    {
        @Override
        public String getStatus()
        {
            return "aite";
        }

        @Override
        public String getThumb()
        {
            return "thumb";
        }

        @Override
        public String getFormat()
        {
            return "format";
        }

        @Override
        public String getTitle()
        {
            return "title";
        }

        @Override
        public String getCatno()
        {
            return "catNo";
        }

        @Override
        public String getArtist()
        {
            return "releaseArtist";
        }

        @Override
        public String getId()
        {
            return "123";
        }
    }
}
