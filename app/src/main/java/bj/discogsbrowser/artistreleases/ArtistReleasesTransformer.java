package bj.discogsbrowser.artistreleases;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.NonNull;

/**
 * Created by Josh Laird on 08/05/2017.
 */
@Singleton
public class ArtistReleasesTransformer implements SingleTransformer<List<ArtistRelease>, List<ArtistRelease>>
{
    private String filterText = "";

    @Inject
    public ArtistReleasesTransformer()
    {

    }

    @Override
    public SingleSource<List<ArtistRelease>> apply(@NonNull Single<List<ArtistRelease>> upstream)
    {
        return upstream.flattenAsObservable(releases ->
                releases)
                .filter(artistRelease ->
                        (artistRelease.getTitle() != null && artistRelease.getTitle().toLowerCase().contains(filterText)) ||
                                (artistRelease.getYear() != null && artistRelease.getYear().toLowerCase().contains(filterText)))
                .toList();
    }

    public void setFilterText(CharSequence charSequence)
    {
        filterText = charSequence.toString().toLowerCase();
    }
}
