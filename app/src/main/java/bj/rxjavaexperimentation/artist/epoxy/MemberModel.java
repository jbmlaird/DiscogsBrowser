package bj.rxjavaexperimentation.artist.epoxy;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
@EpoxyModelClass(layout = R.layout.item_member)
public abstract class MemberModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClick;
    @EpoxyAttribute String name;
    @BindView(R.id.lytMember) LinearLayout lytMember;
    @BindView(R.id.tvName) TextView tvName;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvName.setText(name);
        lytMember.setOnClickListener(onClick);
    }
}
