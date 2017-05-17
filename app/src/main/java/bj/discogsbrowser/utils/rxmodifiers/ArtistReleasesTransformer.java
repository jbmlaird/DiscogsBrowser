package bj.discogsbrowser.utils.rxmodifiers;

import java.util.List;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.NonNull;

/**
 * Created by Josh Laird on 08/05/2017.
 * <p>
 * Transformer to filter ArtistReleases by a query given.
 */
public class ArtistReleasesTransformer implements SingleTransformer<List<ArtistRelease>, List<ArtistRelease>>
{
    private String filterText = "";

    /**
     * Query to filter the {@link ArtistRelease}s by.
     *
     * @param charSequence Text to filter by.
     */
    public void setFilterText(CharSequence charSequence)
    {
        filterText = charSequence.toString().toLowerCase();
    }

    /**
     * Applies the filtered text to the artist releases.
     *
     * @param upstream {@link Single} containing {@link ArtistRelease}s.
     * @return Filtered {@link Single} containing {@link ArtistRelease}s.
     */
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
}
