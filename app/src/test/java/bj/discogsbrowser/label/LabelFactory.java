package bj.discogsbrowser.label;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.label.Label;
import bj.discogsbrowser.model.labelrelease.LabelRelease;
import bj.discogsbrowser.model.release.Image;

/**
 * Created by Josh Laird on 09/05/2017.
 */
public class LabelFactory
{
    public TestLabel getLabel()
    {
        return new TestLabel();
    }

    public List<LabelRelease> getThreeLabelReleases()
    {
        LabelRelease[] labelReleases1 = {new LabelRelease(), new LabelRelease(), new LabelRelease()};
        return Arrays.asList(labelReleases1);
    }

    public List<LabelRelease> getSixLabelReleases()
    {
        LabelRelease[] labelReleases1 = {new LabelRelease(), new LabelRelease(), new LabelRelease(), new LabelRelease(), new LabelRelease(), new LabelRelease()};
        return Arrays.asList(labelReleases1);
    }

    private class TestLabel extends Label
    {
        @Override
        public List<Image> getImages()
        {
            Image image = new Image();
            image.setUri("");
            return Arrays.asList(image);
        }
    }
}
