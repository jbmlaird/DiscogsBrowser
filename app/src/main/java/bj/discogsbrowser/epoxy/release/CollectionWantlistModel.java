package bj.discogsbrowser.epoxy.release;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 26/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_collection_wantlist)
public abstract class CollectionWantlistModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.btnCollection) CircularProgressButton btnCollection;
    @BindView(R.id.btnWantlist) CircularProgressButton btnWantlist;
    @EpoxyAttribute CollectionWantlistPresenter presenter;
    @EpoxyAttribute boolean inCollection;
    @EpoxyAttribute boolean inWantlist;
    @EpoxyAttribute String instanceId;
    @EpoxyAttribute Context context;
    @EpoxyAttribute String releaseId;
    @EpoxyAttribute String folderId;
    private Unbinder unbinder;

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        btnCollection.dispose();
        btnWantlist.dispose();
        unbinder.unbind();
        Log.e("CollectionWantlistModel", "unbind");
    }

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        presenter.bind(inCollection, inWantlist, instanceId, releaseId, btnWantlist, btnCollection);
        if (presenter.isInCollection())
            btnCollection.setText("Remove from Collection");
        if (presenter.isInWantlist())
            btnWantlist.setText("Remove from Wantlist");
    }

    @OnClick(R.id.btnCollection)
    public void onCollectionClicked()
    {
        btnCollection.startAnimation();
        if (!presenter.isInCollection())
            presenter.addToCollection();
        else
            presenter.removeFromCollection();
    }

    @OnClick(R.id.btnWantlist)
    public void onWantlistClicked()
    {
        btnWantlist.startAnimation();
        if (!presenter.isInWantlist())
            presenter.addToWantlist();
        else
            presenter.removeFromWantlist();
    }
}