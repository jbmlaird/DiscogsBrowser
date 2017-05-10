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
 * <p>
 * TODO: Refactor
 * This goes strongly against the MVP pattern by jamming network calls in here.
 * I'm unable to pass in Presenter due to the Controller being DI'd into the Presenter and circular dependencies arising.
 */
@EpoxyModelClass(layout = R.layout.model_collection_wantlist)
public abstract class CollectionWantlistModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute Context context;
    @EpoxyAttribute public boolean inCollection;
    @EpoxyAttribute public boolean inWantlist;
    @EpoxyAttribute String releaseId;
    @EpoxyAttribute String folderId;
    public @EpoxyAttribute String instanceId;
    @EpoxyAttribute CollectionWantlistInteractor collectionWantlistInteractor;
    public @BindView(R.id.btnCollection) CircularProgressButton btnCollection;
    public @BindView(R.id.btnWantlist) CircularProgressButton btnWantlist;
    private Unbinder unbinder;

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
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
            collectionWantlistInteractor.addToCollection(this, releaseId);
        else
            collectionWantlistInteractor.removeFromCollection(this, releaseId, instanceId);
    }

    @OnClick(R.id.btnWantlist)
    public void onWantlistClicked()
    {
        btnWantlist.startAnimation();
        if (!inWantlist)
            collectionWantlistInteractor.addToWantlist(this, releaseId);
        else
            collectionWantlistInteractor.removeFromWantlist(this, releaseId);
    }
}