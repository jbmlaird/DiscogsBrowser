package bj.discogsbrowser.rxmodifiers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 11/04/2017.
 */
public class ArtistResultFunction implements Function<List<ArtistRelease>, List<ArtistRelease>>
{
    private String parameterToMapTo = "";

    @Inject
    public ArtistResultFunction()
    {

    }

    public void setParameterToMapTo(String parameterToMapTo)
    {
        this.parameterToMapTo = parameterToMapTo;
    }

    @Override
    public List<ArtistRelease> apply(@NonNull List<ArtistRelease> artistReleases) throws Exception
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
