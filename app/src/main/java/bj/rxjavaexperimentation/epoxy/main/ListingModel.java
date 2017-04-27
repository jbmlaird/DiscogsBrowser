package bj.rxjavaexperimentation.epoxy.main;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.utils.DateFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_listing)
public abstract class ListingModel extends EpoxyModel<ConstraintLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String releaseName;
    @EpoxyAttribute String datePosted;
    @BindView(R.id.lytListing) ConstraintLayout lytListing;
    @BindView(R.id.tvReleaseName) TextView tvReleaseName;
    @BindView(R.id.tvDatePosted) TextView tvDatePosted;
    private DateFormatter dateFormatter;

    public ListingModel(DateFormatter dateFormatter)
    {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void bind(ConstraintLayout view)
    {
        ButterKnife.bind(this, view);
        tvReleaseName.setText(releaseName);
        tvDatePosted.setText("Listed: " + dateFormatter.formatIsoDate(datePosted));
        lytListing.setOnClickListener(onClickListener);
    }
}
