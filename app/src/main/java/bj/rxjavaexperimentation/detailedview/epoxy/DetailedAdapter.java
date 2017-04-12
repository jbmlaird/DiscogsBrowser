package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;

import com.airbnb.epoxy.EpoxyAdapter;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.detailedview.DetailedBodyModelPresenter;
import bj.rxjavaexperimentation.detailedview.DetailedPresenter;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.LabelRelease;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;

/**
 * Created by Josh Laird on 07/04/2017.
 * <p>
 * Epoxy adapter to populate RecyclerView.
 * // TODO: Dependency inject this class
 */
public class DetailedAdapter extends EpoxyAdapter
{
    private final DetailedHeaderModel_ detailedHeaderModel;
    private DetailedArtistBodyModel_ detailedArtistBodyModel;
    private DetailedLabelModel_ detailedLabelModel;
    private DetailedReleaseModel_ detailedReleaseModel;
    private final DetailedPresenter detailedPresenter;
    private final String title;
    private Context context;
    private DetailedBodyModelPresenter detailedBodyModelPresenter;
    private ArtistsBeautifier artistsBeautifier;

    public DetailedAdapter(DetailedPresenter detailedPresenter, Context context, String title, DetailedBodyModelPresenter detailedBodyModelPresenter, ArtistsBeautifier artistsBeautifier)
    {
        enableDiffing();
        this.detailedBodyModelPresenter = detailedBodyModelPresenter;
        this.detailedPresenter = detailedPresenter;
        this.title = title;
        this.context = context;
        this.artistsBeautifier = artistsBeautifier;
        // TODO: Move this to a method
        detailedHeaderModel = new DetailedHeaderModel_(context)
                .title(title);
        addModel(detailedHeaderModel);
    }

    public void addArtist(ArtistResult artist)
    {
        if (artist.getImages() != null)
            detailedHeaderModel.imageUrl(artist.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = artist.getProfile();
        notifyModelChanged(detailedHeaderModel);

        detailedArtistBodyModel = new DetailedArtistBodyModel_(detailedPresenter, context, detailedBodyModelPresenter);
        detailedArtistBodyModel.artistId(String.valueOf(artist.getId()));
        detailedArtistBodyModel.members = artist.getMembers();
        detailedArtistBodyModel.links = artist.getUrls();
        detailedArtistBodyModel.title(title);
        addModel(detailedArtistBodyModel);
    }

    public void addRelease(Release release)
    {
        if (release.getImages() != null)
            detailedHeaderModel.imageUrl(release.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = artistsBeautifier.formatArtists(release.getArtists());
        notifyModelChanged(detailedHeaderModel);

        addModel(new TracklistModel_(context, release.getTracklist()));

//        detailedReleaseModel = new DetailedReleaseModel_();
//        detailedReleaseModel.tracklist(release.getTracklist());
    }


    public void addMaster(Master master)
    {
        if (master.getImages() != null)
            detailedHeaderModel.imageUrl(master.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = artistsBeautifier.formatArtists(master.getArtists());
        notifyModelChanged(detailedHeaderModel);
    }

    public void addLabel(Label label)
    {
        if (label.getImages() != null)
            detailedHeaderModel.imageUrl(label.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = label.getProfile();
        notifyModelChanged(detailedHeaderModel);

        detailedLabelModel = new DetailedLabelModel_(context, detailedPresenter);
        detailedLabelModel.discogsUrl(label.getUri());
        detailedLabelModel.labelId(label.getId());
        detailedLabelModel.labelName(label.getProfile());
        addModel(detailedLabelModel);
    }

    public void addLabelReleases(List<LabelRelease> labelReleases)
    {
        detailedLabelModel.labelReleases = labelReleases;
        notifyModelChanged(detailedLabelModel);
    }

    public void setReleaseListings(ArrayList<Listing> listings)
    {

    }
}
