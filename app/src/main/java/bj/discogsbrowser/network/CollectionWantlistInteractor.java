package bj.discogsbrowser.network;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import bj.discogsbrowser.epoxy.release.CollectionWantlistModel;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import es.dmoral.toasty.Toasty;

/**
 * Created by Josh Laird on 10/05/2017.
 */

public class CollectionWantlistInteractor
{
    private Context context;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public CollectionWantlistInteractor(Context context, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider)
    {
        this.context = context;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    public void addToCollection(CollectionWantlistModel model, String releaseId)
    {
        discogsInteractor.addToCollection(releaseId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.getInstanceId() != null)
                            {
                                model.instanceId = result.getInstanceId();
                                model.inCollection = true;
                                model.btnCollection.revertAnimation(() ->
                                        model.btnCollection.setText("Remove from Collection"));
                            }
                            else
                            {
                                Toasty.error(context, "Unable to add to Collection", Toast.LENGTH_SHORT, true).show();
                                model.btnCollection.revertAnimation();
                            }
                        },
                        error ->
                        {
                            Toasty.error(context, "Unable to add to Collection", Toast.LENGTH_SHORT, true).show();
                            error.printStackTrace();
                            model.btnCollection.revertAnimation();
                        });
    }

    public void removeFromCollection(CollectionWantlistModel model, String releaseId, String instanceId)
    {
        discogsInteractor.removeFromCollection(releaseId, instanceId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                model.inCollection = false;
                                model.btnCollection.revertAnimation(() ->
                                        model.btnCollection.setText("Add to Collection"));
                            }
                            else
                            {
                                Toasty.error(context, "Unable to remove from Collection", Toast.LENGTH_SHORT, true).show();
                                model.btnCollection.revertAnimation();
                            }
                        },
                        error ->
                        {
                            Toasty.error(context, "Unable to remove from Collection", Toast.LENGTH_SHORT, true).show();
                            model.btnCollection.revertAnimation();
                        });
    }

    public void addToWantlist(CollectionWantlistModel model, String releaseId)
    {
        discogsInteractor.addToWantlist(releaseId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            model.inWantlist = true;
                            model.btnWantlist.revertAnimation(() ->
                                    model.btnWantlist.setText("Remove from Wantlist"));
                        },
                        error ->
                        {
                            Toasty.error(context, "Unable to add to Wantlist", Toast.LENGTH_SHORT, true).show();
                            model.btnWantlist.revertAnimation();
                        });
    }

    public void removeFromWantlist(CollectionWantlistModel model, String releaseId)
    {
        discogsInteractor.removeFromWantlist(releaseId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                model.inWantlist = false;
                                model.btnWantlist.revertAnimation(() ->
                                        model.btnWantlist.setText("Add to Wantlist"));
                            }
                            else
                            {
                                Toasty.error(context, "Unable to remove from Wantlist", Toast.LENGTH_SHORT, true).show();
                                model.btnWantlist.revertAnimation();
                            }
                        },
                        error ->
                        {
                            Toasty.error(context, "Unable to remove from Wantlist", Toast.LENGTH_SHORT, true).show();
                            model.btnWantlist.revertAnimation();
                        });
    }
}
