package bj.discogsbrowser.master;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.version.Version;

/**
 * Created by Josh Laird on 15/05/2017.
 */
public class MasterVersionsFactory
{
    public static List<Version> getTwoMasterVersions()
    {
        return Arrays.asList(new TestVersion("testVersionId1", "thumb1", "testVersionTitle1", "testVersionFormat1"),
                new TestVersion("testVersionId2", "thumb2", "testVersionTitle2", "testVersionFormat2"));
    }

    private static class TestVersion extends Version
    {
        private TestVersion(String id, String thumb, String title, String format)
        {
            setId(id);
            setThumb(thumb);
            setTitle(title);
            setFormat(format);
        }
    }
}
