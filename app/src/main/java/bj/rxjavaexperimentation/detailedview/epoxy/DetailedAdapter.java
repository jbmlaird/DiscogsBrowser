package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;

import com.airbnb.epoxy.EpoxyAdapter;

import java.util.List;

import bj.rxjavaexperimentation.detailedview.DetailedBodyModelPresenter;
import bj.rxjavaexperimentation.detailedview.DetailedPresenter;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Artist;
import bj.rxjavaexperimentation.model.release.Release;

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
    private final DetailedPresenter detailedPresenter;
    private final String title;
    private Context context;
    private DetailedBodyModelPresenter detailedBodyModelPresenter;

    public DetailedAdapter(DetailedPresenter detailedPresenter, Context context, String title, DetailedBodyModelPresenter detailedBodyModelPresenter)
    {
        enableDiffing();
        this.detailedBodyModelPresenter = detailedBodyModelPresenter;
        this.detailedPresenter = detailedPresenter;
        this.title = title;
        this.context = context;
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
        detailedHeaderModel.subtitle = getArtists(release.getArtists());
        notifyModelChanged(detailedHeaderModel);
    }


    public void addMaster(Master master)
    {
        if (master.getImages() != null)
            detailedHeaderModel.imageUrl(master.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = getArtists(master.getArtists());
        notifyModelChanged(detailedHeaderModel);
    }

    public void addLabel(Label label)
    {
        if (label.getImages() != null)
            detailedHeaderModel.imageUrl(label.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = label.getProfile();
        notifyModelChanged(detailedHeaderModel);
    }

    /**
     * Extract the artists from the model, comma-separate then replace final comma with ampersand.
     *
     * @param artists List of artists in model.
     * @return Beautiful to look at artist string.
     */
    private String getArtists(List<Artist> artists)
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
