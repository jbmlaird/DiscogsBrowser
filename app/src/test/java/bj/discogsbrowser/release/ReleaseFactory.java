package bj.discogsbrowser.release;

import java.util.Arrays;

import bj.discogsbrowser.model.release.Label;
import bj.discogsbrowser.model.release.Release;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Created by Josh Laird on 10/05/2017.
 */

public class ReleaseFactory
{
    public Release getReleaseNoLabelNoneForSale()
    {
        Release release = new Release();
        release.setNumForSale(0);
        release.setLabels(Collections.emptyList());
        return release;
    }

    public Release getReleaseWithLabelNoneForSale()
    {
        Release release = new Release();
        release.setNumForSale(0);
        release.setLabels(Arrays.asList(getLabel()));
        return release;
    }

    private Label getLabel()
    {
        Label label = new Label();
        label.setId("123");
        return label;
    }
}
