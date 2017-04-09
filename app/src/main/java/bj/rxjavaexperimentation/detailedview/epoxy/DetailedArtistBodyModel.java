package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.detailedview.DetailedPresenter;
import bj.rxjavaexperimentation.model.release.Release;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 09/04/2017.
 */

@EpoxyModelClass(layout = R.layout.model_detailed_artist_body)
public abstract class DetailedArtistBodyModel extends EpoxyModel<LinearLayout>
{
    private final DetailedPresenter detailedPresenter;
    private final Context context;
    @EpoxyAttribute String artistId;
    @EpoxyAttribute List<Release> releaseList;
    @BindView(R.id.btnViewReleases) Button btnViewReleases;

    public DetailedArtistBodyModel(DetailedPresenter detailedPresenter, Context context)
    {
        this.detailedPresenter = detailedPresenter;
        this.context = context;
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
//        btnViewReleases.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(this, ListActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
    }

    // This was going to display a preview of the artists releases on their page
    // but as the Discogs API doesn't return thumbnails from this request it would
    // look pretty ugly :/
//    void displayReleases()
//    {
//        for (Release release : releaseList)
//        {
//            TextView textview = new TextView(context);
//            textview.setText(release.getThumb());
//            lytReleases.addView(textview);
//        }
//    }
}
