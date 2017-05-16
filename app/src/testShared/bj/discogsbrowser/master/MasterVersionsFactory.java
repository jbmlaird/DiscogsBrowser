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

    public static List<Version> getSixMasterVersions()
    {
        return Arrays.asList(new TestVersion("testVersionId1", "thumb1", "testVersionTitle1", "testVersionFormat1"),
                new TestVersion("testVersionId2", "thumb2", "testVersionTitle2", "testVersionFormat2"),
                new TestVersion("testVersionId3", "thumb3", "testVersionTitle3", "testVersionFormat3"),
                new TestVersion("testVersionId4", "thumb4", "testVersionTitle4", "testVersionFormat4"),
                new TestVersion("testVersionId5", "thumb5", "testVersionTitle5", "testVersionFormat5"),
                new TestVersion("testVersionId6", "thumb6", "testVersionTitle6", "testVersionFormat6"));
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
