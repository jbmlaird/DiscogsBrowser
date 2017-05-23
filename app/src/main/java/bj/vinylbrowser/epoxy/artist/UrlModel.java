package bj.vinylbrowser.epoxy.artist;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.joanzapata.iconify.widget.IconTextView;

import bj.vinylbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 21/04/2017.
 */
@EpoxyModelClass(layout = R.layout.item_artist_link)
public abstract class UrlModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.lytLink) LinearLayout lytLink;
    @BindView(R.id.tvIcon) IconTextView tvIcon;
    @BindView(R.id.tvText) TextView tvText;
    @EpoxyAttribute View.OnClickListener onLinkClick;
    @EpoxyAttribute String friendlyText;
    @EpoxyAttribute String hexCode;
    @EpoxyAttribute String fontAwesomeCode;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvIcon.setText(fontAwesomeCode);
        tvIcon.setTextColor(Color.parseColor(hexCode));
        tvText.setText(friendlyText);
        lytLink.setOnClickListener(onLinkClick);
    }
}
