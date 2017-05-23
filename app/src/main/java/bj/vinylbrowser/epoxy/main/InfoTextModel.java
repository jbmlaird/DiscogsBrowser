package bj.vinylbrowser.epoxy.main;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.vinylbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 02/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_info_text)
public abstract class InfoTextModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String infoText;
    @BindView(R.id.lytVerifyEmail) LinearLayout lytVerifyEmail;
    @BindView(R.id.tvInfo) TextView tvInfo;
    private Unbinder unbinder;

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        lytVerifyEmail.setOnClickListener(onClickListener);
        tvInfo.setText(infoText);
    }
}
