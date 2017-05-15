package bj.discogsbrowser.master;

import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.master.Master;
import bj.discogsbrowser.model.release.Image;
import bj.discogsbrowser.model.version.Version;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Created by Josh Laird on 09/05/2017.
 */
public class MasterFactory
{
    public Master getMaster()
    {
        Master master = new Master();
        Image[] images = {new Image(), new Image()};
        master.setImages(Arrays.asList(images));
        return master;
    }

    public List<Version> getEmptyMasterVersions()
    {
        return Collections.emptyList();
    }

    public List<Version> getTwoMasterVersions()
    {
        Version[] versions = {new Version(), new Version()};
        return Arrays.asList(versions);
    }

    public List<Version> getSixMasterVersions()
    {
        Version[] versions = {new Version(), new Version(), new Version(), new Version(), new Version(), new Version()};
        return Arrays.asList(versions);
    }
}
