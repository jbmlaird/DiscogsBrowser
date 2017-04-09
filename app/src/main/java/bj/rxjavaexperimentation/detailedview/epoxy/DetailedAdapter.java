package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;

import com.airbnb.epoxy.EpoxyAdapter;

import java.util.List;

import bj.rxjavaexperimentation.detailedview.DetailedPresenter;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.release.Release;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public class DetailedAdapter extends EpoxyAdapter
{
    private final DetailedHeaderModel_ detailedHeaderModel;
    private DetailedArtistBodyModel_ detailedArtistBodyModel;
    private final DetailedPresenter detailedPresenter;
    private final String title;
    private Context context;

    public DetailedAdapter(DetailedPresenter detailedPresenter, Context context, String title)
    {
        enableDiffing();
        this.detailedPresenter = detailedPresenter;
        this.title = title;
        this.context = context;
        detailedHeaderModel = new DetailedHeaderModel_(context)
                .title(title);
        addModel(detailedHeaderModel);
    }

    public void addArtist(ArtistResult artist)
    {
        detailedHeaderModel.imageUrl(artist.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = artist.getProfile();
        detailedArtistBodyModel = new DetailedArtistBodyModel_(detailedPresenter, context);
        detailedArtistBodyModel.artistId(String.valueOf(artist.getId()));
        addModel(detailedArtistBodyModel);

        notifyModelChanged(detailedHeaderModel);
    }

    public void addRelease(Release release)
    {
        detailedHeaderModel.imageUrl(release.getImages().get(0).getResourceUrl());
        notifyModelChanged(detailedHeaderModel);
    }

    public void addArtistBody(List<Release> releases)
    {
        detailedArtistBodyModel.releaseList = releases;
//        detailedArtistBodyModel.displayReleases();
        notifyModelChanged(detailedArtistBodyModel);
    }
}
