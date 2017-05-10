package bj.discogsbrowser.epoxy.release;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
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
    public @EpoxyAttribute boolean inCollection;
    public @EpoxyAttribute boolean inWantlist;
    public @EpoxyAttribute String instanceId;
    @EpoxyAttribute CollectionWantlistInteractor collectionWantlistInteractor;
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
        if (inCollection)
            btnCollection.setText("Remove from Collection");
        if (inWantlist)
            btnWantlist.setText("Remove from Wantlist");
    }

    @OnClick(R.id.btnCollection)
    public void onCollectionClicked()
    {
        btnCollection.startAnimation();
        if (!inCollection)
            collectionWantlistInteractor.addToCollection(this, releaseId, btnCollection);
        else
            collectionWantlistInteractor.removeFromCollection(this, releaseId, instanceId, btnCollection);
    }

    @OnClick(R.id.btnWantlist)
    public void onWantlistClicked()
    {
        btnWantlist.startAnimation();
        if (!inWantlist)
            collectionWantlistInteractor.addToWantlist(this, releaseId, btnWantlist);
        else
            collectionWantlistInteractor.removeFromWantlist(this, releaseId, btnWantlist);
    }
}