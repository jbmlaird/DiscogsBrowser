package bj.discogsbrowser.artist;

import bj.discogsbrowser.model.common.Artist;

/**
 * Created by Josh Laird on 16/05/2017.
 */

public class ArtistFactory
{
    public static Artist buildSingleArtist()
    {
        Artist artist = new Artist();
        artist.setName("ye son");
        return artist;
    }
}
