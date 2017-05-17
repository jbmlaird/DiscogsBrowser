package bj.discogsbrowser.artistreleases;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;

/**
 * Created by Josh Laird on 15/05/2017.
 * <p>
 * Wrapper for BehaviorRelay for DI.
 */

public class ArtistReleaseBehaviorRelay
{
    private BehaviorRelay<List<ArtistRelease>> artistReleaseBehaviorRelay;

    public ArtistReleaseBehaviorRelay()
    {
        artistReleaseBehaviorRelay = BehaviorRelay.create();
    }

    public BehaviorRelay<List<ArtistRelease>> getArtistReleaseBehaviorRelay()
    {
        return artistReleaseBehaviorRelay;
    }
}
