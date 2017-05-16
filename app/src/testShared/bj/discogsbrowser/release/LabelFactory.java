package bj.discogsbrowser.release;

import bj.discogsbrowser.model.release.Label;

/**
 * Created by Josh Laird on 16/05/2017.
 */

public class LabelFactory
{
    public static Label buildLabel()
    {
        Label label = new Label();
        label.setCatno("");
        label.setThumb("");
        label.setName("labelName");
        label.setId("labelId");
        label.setResourceUrl("");
        return label;
    }
}
