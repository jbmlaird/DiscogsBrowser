package bj.discogsbrowser.singlelist;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.collection.CollectionRelease;

import static bj.discogsbrowser.singlelist.WantFactory.buildBasicInformation;

/**
 * Created by Josh Laird on 16/05/2017.
 */
public class CollectionFactory
{
    public static List<CollectionRelease> getThreeCollectionReleases()
    {
        return Arrays.asList(buildCollectionRelease("collectionId1", "collectionTitle1"),
                buildCollectionRelease("collectionId2", "collectionTitle2"), buildCollectionRelease("collectionId3", "collectionTitle3"));
    }

    private static CollectionRelease buildCollectionRelease(String id, String title)
    {
        CollectionRelease collectionRelease = new CollectionRelease();
        collectionRelease.setId(id);
        collectionRelease.setBasicInformation(buildBasicInformation(title));
        return collectionRelease;
    }
}
