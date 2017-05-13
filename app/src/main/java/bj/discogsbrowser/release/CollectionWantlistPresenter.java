package bj.discogsbrowser.release;

import android.content.Context;

import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.discogsbrowser.wrappers.ToastyWrapper;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Josh Laird on 10/05/2017.
 */
public class CollectionWantlistPresenter
{
    private Context context;
    private CollectionWantlistInteractor collectionWantlistInteractor;
    private SharedPrefsManager sharedPrefsManager;
    private MySchedulerProvider mySchedulerProvider;
    private ToastyWrapper toasty;
    private String instanceId;
    private boolean inCollection;
    private boolean inWantlist;

    public CollectionWantlistPresenter(Context context, CollectionWantlistInteractor collectionWantlistInteractor,
                                       SharedPrefsManager sharedPrefsManager, MySchedulerProvider mySchedulerProvider,
                                       ToastyWrapper toasty)
    {
        this.context = context;
        this.collectionWantlistInteractor = collectionWantlistInteractor;
        this.sharedPrefsManager = sharedPrefsManager;
        this.mySchedulerProvider = mySchedulerProvider;
        this.toasty = toasty;
    }

    public void bind(boolean inCollection, boolean inWantlist, String instanceId)
    {
        this.inCollection = inCollection;
        this.inWantlist = inWantlist;
        this.instanceId = instanceId;
    }

    public void addToCollection(String releaseId, CircularProgressButton btnCollection)
    {
        sharedPrefsManager.setFetchNextCollection("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        collectionWantlistInteractor.addToCollection(releaseId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.getInstanceId() != null)
                            {
                                instanceId = result.getInstanceId();
                                inCollection = true;
                                btnCollection.revertAnimation(() -> btnCollection.setText("Remove from Collection"));
                            }
                            else
                            {
                                toasty.error("Unable to add to Collection");
                                btnCollection.revertAnimation(() -> btnCollection.setText("Add to Collection"));
                            }
                        },
                        error ->
                        {
                            toasty.error("Unable to add to Collection");
                            btnCollection.revertAnimation(() -> btnCollection.setText("Add to Collection"));
                        });
    }

    public void removeFromCollection(String releaseId, CircularProgressButton btnCollection)
    {
        sharedPrefsManager.setFetchNextCollection("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        collectionWantlistInteractor.removeFromCollection(releaseId, instanceId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                inCollection = false;
                                btnCollection.revertAnimation(() -> btnCollection.setText("Add to Collection"));
                            }
                            else
                            {
                                toasty.error("Unable to remove from Collection");
                                btnCollection.revertAnimation(() -> btnCollection.setText("Remove from Collection"));
                            }
                        },
                        error ->
                        {
                            toasty.error("Unable to remove from Collection");
                            btnCollection.revertAnimation(() -> btnCollection.setText("Remove from Collection"));
                        });
    }

    public void addToWantlist(String releaseId, CircularProgressButton btnWantlist)
    {
        sharedPrefsManager.setFetchNextWantlist("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        collectionWantlistInteractor.addToWantlist(releaseId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            inWantlist = true;
                            btnWantlist.revertAnimation(() -> btnWantlist.setText("Remove from Wantlist"));
                        },
                        error ->
                        {
                            toasty.error("Unable to add to Wantlist");
                            btnWantlist.revertAnimation(() -> btnWantlist.setText("Add to Wantlist"));
                        });
    }

    public void removeFromWantlist(String releaseId, CircularProgressButton btnWantlist)
    {
        sharedPrefsManager.setFetchNextWantlist("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        collectionWantlistInteractor.removeFromWantlist(releaseId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                inWantlist = false;
                                btnWantlist.revertAnimation(() -> btnWantlist.setText("Add to Wantlist"));
                            }
                            else
                            {
                                toasty.error("Unable to remove from Wantlist");
                                btnWantlist.revertAnimation(() -> btnWantlist.setText("Remove from Wantlist"));
                            }
                        },
                        error ->
                        {
                            toasty.error("Unable to remove from Wantlist");
                            btnWantlist.revertAnimation(() -> btnWantlist.setText("Remove from Wantlist"));
                        });

    }

    public boolean isInCollection()
    {
        return inCollection;
    }

    public boolean isInWantlist()
    {
        return inWantlist;
    }
}
