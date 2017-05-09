package bj.discogsbrowser.master;

import java.util.List;

import bj.discogsbrowser.model.master.Master;
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
        return master;
    }

    public List<Version> getMasterVersions()
    {
        Version[] versions = {new Version(), new Version()};
        return Arrays.asList(versions);
    }
}
