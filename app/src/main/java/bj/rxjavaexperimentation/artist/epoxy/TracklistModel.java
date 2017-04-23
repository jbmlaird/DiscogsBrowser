package bj.rxjavaexperimentation.artist.epoxy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.release.Tracklist;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josh Laird on 12/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_tracklist)
public abstract class TracklistModel extends EpoxyModel<LinearLayout>
{
    private Context context;
    private LayoutInflater layoutInflater;
    @EpoxyAttribute List<Tracklist> tracklist = new ArrayList<>();
    @BindView(R.id.lytTracklist) LinearLayout lytTracklist;
    @BindView(R.id.lytViewMore) LinearLayout lytViewMore;
    @BindView(R.id.tvViewMore) TextView tvViewMore;

    public TracklistModel(Context context, List<Tracklist> tracklist)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.tracklist = tracklist;
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        for (Tracklist track : tracklist)
        {
            View tracklistView = layoutInflater.inflate(R.layout.item_tracklist, lytTracklist, false);
            TracklistViewHolder tracklistViewHolder = new TracklistViewHolder(tracklistView);
            tracklistViewHolder.tvTrackNumber.setText(track.getPosition());
            tracklistViewHolder.tvTrack.setText(track.getTitle());
            lytTracklist.addView(tracklistView);
            if (tracklist.indexOf(track) == 4 && tracklist.size() > 5)
            {
                lytViewMore.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @OnClick(R.id.lytViewMore)
    public void showFullTracklist()
    {
        for (int i = 5; i < tracklist.size(); i++)
        {
            View tracklistView = layoutInflater.inflate(R.layout.item_tracklist, null, false);
            TracklistViewHolder tracklistViewHolder = new TracklistViewHolder(tracklistView);
            tracklistViewHolder.tvTrackNumber.setText(tracklist.get(i).getPosition());
            tracklistViewHolder.tvTrack.setText(tracklist.get(i).getTitle());
            lytTracklist.addView(tracklistView);
        }
        lytViewMore.setVisibility(View.GONE);
    }

    class TracklistViewHolder
    {
        @BindView(R.id.tvTrackNumber) TextView tvTrackNumber;
        @BindView(R.id.tvTrack) TextView tvTrack;

        public TracklistViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
