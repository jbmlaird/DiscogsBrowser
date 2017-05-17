package bj.discogsbrowser.utils;

import java.util.List;

import bj.discogsbrowser.model.common.Artist;

/**
 * Created by Josh Laird on 12/04/2017.
 * <p>
 * Makes the String contained in {@link Artist} beautiful, just like you :)
 */
public class ArtistsBeautifier
{
    /**
     * Extract the artists from the model, comma-separate then replace final comma with ampersand.
     *
     * @param artists List of artists in model.
     * @return Beautiful to look at Artist String.
     */
    public String formatArtists(List<Artist> artists)
    {
        StringBuilder artistStringBuilder = new StringBuilder();
        if (artists.size() > 1)
        {
            // No separator before first element
            String separator = "";
            for (Artist artist : artists)
            {
                artistStringBuilder.append(separator).append(artist.getName());
                separator = ", ";
            }
            artistStringBuilder.replace(artistStringBuilder.toString().lastIndexOf(","), artistStringBuilder.toString().lastIndexOf(",") + 1, " &");
        }
        else
            artistStringBuilder.append(artists.get(0).getName());
        return artistStringBuilder.toString();
    }
}
