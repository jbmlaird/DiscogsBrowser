package bj.discogsbrowser.release;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;

/**
 * Created by Josh Laird on 10/05/2017.
 */
public class CollectionWantlistPresenter
{
    private Context context;
    private CollectionWantlistInteractor collectionWantlistInteractor;
    private SharedPrefsManager sharedPrefsManager;
    private MySchedulerProvider mySchedulerProvider;
    private String instanceId;
    private boolean inCollection;
    private boolean inWantlist;

    @Inject
    public CollectionWantlistPresenter(Context context, CollectionWantlistInteractor collectionWantlistInteractor, SharedPrefsManager sharedPrefsManager, MySchedulerProvider mySchedulerProvider)
    {
        this.context = context;
        this.collectionWantlistInteractor = collectionWantlistInteractor;
        this.sharedPrefsManager = sharedPrefsManager;
        this.mySchedulerProvider = mySchedulerProvider;
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
                            btnCollection.revertAnimation();
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
                            btnCollection.revertAnimation();
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
                            Toasty.error(context, "Unable to add to Wantlist", Toast.LENGTH_SHORT, true).show();
                            btnWantlist.revertAnimation();
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

    public boolean isInCollection()
    {
        return inCollection;
    }

    public boolean isInWantlist()
    {
        return inWantlist;
    }
}
