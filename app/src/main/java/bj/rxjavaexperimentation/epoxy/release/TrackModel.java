package bj.rxjavaexperimentation.epoxy.release;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 24/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_track)
public abstract class TrackModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute String trackNumber;
    @EpoxyAttribute String trackName;
    @BindView(R.id.tvTrackNumber) TextView tvTrackNumber;
    @BindView(R.id.tvTrack) TextView tvTrack;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvTrackNumber.setText(trackNumber);
        tvTrack.setText(trackName);
    }
}
