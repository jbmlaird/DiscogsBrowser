package bj.discogsbrowser.utils.rxmodifiers;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 11/04/2017.
 */
public class ArtistResultFunction implements Function<List<ArtistRelease>, List<ArtistRelease>>
{
    private String parameterToMapTo = "";

    /**
     * Sets whether to filter the releases to Releases or Masters.
     *
     * @param parameterToMapTo release or master.
     */
    public void setParameterToMapTo(String parameterToMapTo)
    {
        this.parameterToMapTo = parameterToMapTo;
    }

    /**
     * Applies filter set above.
     *
     * @param artistReleases List of artist releases.
     * @return Filtered list of artist releases.
     */
    @Override
    public List<ArtistRelease> apply(@NonNull List<ArtistRelease> artistReleases)
    {
        ArrayList<ArtistRelease> remixes = new ArrayList<>();
        for (ArtistRelease release : artistReleases)
        {
            if (release.getType().equals(parameterToMapTo))
                remixes.add(release);
        }
        return remixes;
    }
}
