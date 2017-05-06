package bj.discogsbrowser.epoxy.release;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

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
    @EpoxyAttribute boolean inCollection;
    @EpoxyAttribute boolean inWantlist;
    @EpoxyAttribute String releaseId;
    @EpoxyAttribute String folderId;
    @EpoxyAttribute String instanceId;
    @EpoxyAttribute DiscogsInteractor discogsInteractor;
    @EpoxyAttribute MySchedulerProvider mySchedulerProvider;
    @BindView(R.id.btnCollection) CircularProgressButton btnCollection;
    @BindView(R.id.btnWantlist) CircularProgressButton btnWantlist;
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
            discogsInteractor.addToCollection(releaseId)
                    .subscribeOn(mySchedulerProvider.io())
                    .observeOn(mySchedulerProvider.ui())
                    .subscribe(result ->
                            {
                                if (result.getInstanceId() != null)
                                {
                                    instanceId = result.getInstanceId();
                                    inCollection = true;
                                    btnCollection.revertAnimation(() ->
                                            btnCollection.setText("Remove from Collection"));
                                }
                                else
                                {
                                    Toasty.error(context, "Unable to add to Collection", Toast.LENGTH_SHORT, true).show();
                                    btnCollection.revertAnimation();
                                }
                            },
                            error ->
                            {
                                Toasty.error(context, "Unable to add to Collection", Toast.LENGTH_SHORT, true).show();
                                error.printStackTrace();
                                btnCollection.revertAnimation();
                            });
        else
            discogsInteractor.removeFromCollection(releaseId, instanceId)
                    .subscribeOn(mySchedulerProvider.io())
                    .observeOn(mySchedulerProvider.ui())
                    .subscribe(result ->
                            {
                                if (result.isSuccessful())
                                {
                                    inCollection = false;
                                    btnCollection.revertAnimation(() ->
                                            btnCollection.setText("Add to Collection"));
                                }
                                else
                                {
                                    Toasty.error(context, "Unable to remove from Collection", Toast.LENGTH_SHORT, true).show();
                                    btnCollection.revertAnimation();
                                }
                            },
                            error ->
                            {
                                Toasty.error(context, "Unable to remove from Collection", Toast.LENGTH_SHORT, true).show();
                                error.printStackTrace();
                                btnCollection.revertAnimation();
                            });
    }

    @OnClick(R.id.btnWantlist)
    public void onWantlistClicked()
    {
        btnWantlist.startAnimation();
        if (!inWantlist)
            discogsInteractor.addToWantlist(releaseId)
                    .subscribeOn(mySchedulerProvider.io())
                    .observeOn(mySchedulerProvider.ui())
                    .subscribe(result ->
                            {
                                inWantlist = true;
                                btnWantlist.revertAnimation(() ->
                                        btnWantlist.setText("Remove from Wantlist"));
                            },
                            error ->
                            {
                                Toasty.error(context, "Unable to add to Wantlist", Toast.LENGTH_SHORT, true).show();
                                error.printStackTrace();
                                btnWantlist.revertAnimation();
                            });
        else
            discogsInteractor.removeFromWantlist(releaseId)
                    .subscribeOn(mySchedulerProvider.io())
                    .observeOn(mySchedulerProvider.ui())
                    .subscribe(result ->
                            {
                                if (result.isSuccessful())
                                {
                                    inWantlist = false;
                                    btnWantlist.revertAnimation(() ->
                                            btnWantlist.setText("Add to Wantlist"));
                                }
                                else
                                {
                                    Toasty.error(context, "Unable to remove from Wantlist", Toast.LENGTH_SHORT, true).show();
                                    btnWantlist.revertAnimation();
                                }
                            },
                            error ->
                            {
                                Toasty.error(context, "Unable to remove from Wantlist", Toast.LENGTH_SHORT, true).show();
                                btnWantlist.revertAnimation();
                            });
    }
}