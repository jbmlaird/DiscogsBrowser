package bj.rxjavaexperimentation.detailedview.epoxy;

import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 21/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_view_releases)
public abstract class ReleasesModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.lytReleases) LinearLayout lytReleases;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onViewReleasesClicked;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        lytReleases.setOnClickListener(onViewReleasesClicked);
    }
}
