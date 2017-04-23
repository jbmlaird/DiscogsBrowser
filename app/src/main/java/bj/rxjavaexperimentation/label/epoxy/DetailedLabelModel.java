package bj.rxjavaexperimentation.label.epoxy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.bumptech.glide.Glide;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artist.ArtistContract;
import bj.rxjavaexperimentation.model.labelrelease.LabelRelease;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josh Laird on 12/04/2017.
 */

@EpoxyModelClass(layout = R.layout.model_label)
public abstract class DetailedLabelModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute List<LabelRelease> labelReleases = new ArrayList<>();
    @EpoxyAttribute String labelId;
    @EpoxyAttribute String labelName;
    @EpoxyAttribute String discogsUrl;
    @BindView(R.id.lytViewReleasesContainer) LinearLayout lytViewReleasesContainer;
    @BindView(R.id.lytSearchReleases) LinearLayout lytSearchReleases;
    @BindView(R.id.lytReleases) LinearLayout lytReleases;
    private Context context;
    private LayoutInflater mInflater;
    private ArtistContract.View view;

    public DetailedLabelModel(Context context, ArtistContract.View view)
    {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.view = view;
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        displayReleases();
    }

    private void displayReleases()
    {
        for (LabelRelease labelRelease : labelReleases)
        {
            View labelReleaseView = mInflater.inflate(R.layout.model_label_release, lytReleases, false);
            LabelReleaseViewHolder labelReleaseViewHolder = new LabelReleaseViewHolder(labelRelease, labelReleaseView);
            Glide.with(context)
                    .load(labelRelease.getThumb())
                    .placeholder(R.drawable.ic_vinyl)
                    .crossFade()
                    .into(labelReleaseViewHolder.ivImage);
            lytReleases.addView(labelReleaseView);
            if (labelReleases.indexOf(labelRelease) == 4)
            {
                // Display no more than 5 releases
                break;
            }
        }
    }

    @OnClick(R.id.lytViewOnDiscogs)
    public void viewOnDiscogs()
    {
        new FinestWebView.Builder(context).show(discogsUrl);
    }

    /**
     * May have to launch {@link bj.rxjavaexperimentation.singlelist.SingleListActivity} for this.
     */
    @OnClick(R.id.lytSearchReleases)
    public void searchReleases()
    {
        // TODO: To implement.
    }

    @OnClick(R.id.lytViewReleasesContainer)
    public void displayLabelReleases()
    {
//        presenter.displayLabelReleases(labelId, labelName);
        lytViewReleasesContainer.setVisibility(View.GONE);
        lytSearchReleases.setVisibility(View.VISIBLE);
        for (int i = 5; i < labelReleases.size(); i++)
        {
            View labelReleaseView = mInflater.inflate(R.layout.model_label_release, lytReleases, false);
            LabelReleaseViewHolder labelReleaseViewHolder = new LabelReleaseViewHolder(labelReleases.get(i), labelReleaseView);
            Glide.with(context)
                    .load(labelReleases.get(i).getThumb())
                    .placeholder(R.drawable.ic_vinyl)
                    .crossFade()
                    .into(labelReleaseViewHolder.ivImage);
            lytReleases.addView(labelReleaseView);
        }
    }

    class LabelReleaseViewHolder
    {
        @BindView(R.id.lytLabelReleaseContainer) LinearLayout lytLabelReleaseContainer;
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvArtists) TextView tvArtists;
        @BindView(R.id.tvTitle) TextView tvTitle;
        private LabelRelease labelRelease;

        public LabelReleaseViewHolder(LabelRelease labelRelease, View view)
        {
            ButterKnife.bind(this, view);
            this.labelRelease = labelRelease;
            tvTitle.setText(labelRelease.getTitle());
            tvArtists.setText(labelRelease.getArtist());
        }

        @OnClick(R.id.lytLabelReleaseContainer)
        public void showRelease()
        {
        }
    }
}
