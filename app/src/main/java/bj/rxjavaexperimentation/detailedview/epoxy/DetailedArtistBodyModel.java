package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artistreleases.ArtistReleasesActivity;
import bj.rxjavaexperimentation.detailedview.DetailedBodyModelPresenter;
import bj.rxjavaexperimentation.model.artist.Member;
import bj.rxjavaexperimentation.model.release.Release;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 09/04/2017.
 */

@EpoxyModelClass(layout = R.layout.model_detailed_artist_body)
public abstract class DetailedArtistBodyModel extends EpoxyModel<LinearLayout>
{
    private final Context context;
    private final LayoutInflater inflater;
    private DetailedBodyModelPresenter presenter;
    @EpoxyAttribute String title;
    @EpoxyAttribute String artistId;
    @EpoxyAttribute List<Release> releaseList;
    @EpoxyAttribute List<Member> members;
    @EpoxyAttribute List<String> links;
    @BindView(R.id.lytReleases) RelativeLayout lytReleases;
    @BindView(R.id.lytMembersContainer) RelativeLayout lytMembersContainer;
    @BindView(R.id.lytMembers) LinearLayout lytMembers;
    @BindView(R.id.lytLinksContainer) LinearLayout lytLinksContainer;
    @BindView(R.id.lytLinks) LinearLayout lytLinks;

    public DetailedArtistBodyModel(Context context, DetailedBodyModelPresenter presenter)
    {
        this.context = context;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        presenter.displayMembers(lytMembersContainer, lytMembers, members);
        presenter.displaySocialLinks(lytLinksContainer, links, lytLinks);
        lytReleases.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, ArtistReleasesActivity.class);
            intent.putExtra("name", title);
            intent.putExtra("id", artistId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }
}

// Use an extra request here?
//    void displayReleases()
//    {
//        for (Release release : releaseList)
//        {
//            TextView textview = new TextView(context);
//            textview.setText(release.getThumb());
//            lytReleases.addView(textview);
//        }
//    }
