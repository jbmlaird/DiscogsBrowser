package bj.discogsbrowser.epoxy.release;

import android.content.Context;

import bj.discogsbrowser.R;
import bj.discogsbrowser.network.DiscogsInteractor;
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
    private DiscogsInteractor discogsInteractor;
    private SharedPrefsManager sharedPrefsManager;
    private MySchedulerProvider mySchedulerProvider;
    private ToastyWrapper toasty;
    private String instanceId;
    private String releaseId;
    private CircularProgressButton btnWantlist;
    private CircularProgressButton btnCollection;
    private boolean inCollection;
    private boolean inWantlist;

    public CollectionWantlistPresenter(Context context, DiscogsInteractor discogsInteractor,
                                       SharedPrefsManager sharedPrefsManager, MySchedulerProvider mySchedulerProvider,
                                       ToastyWrapper toasty)
    {
        this.context = context;
        this.discogsInteractor = discogsInteractor;
        this.sharedPrefsManager = sharedPrefsManager;
        this.mySchedulerProvider = mySchedulerProvider;
        this.toasty = toasty;
    }

    public void bind(boolean inCollection, boolean inWantlist, String instanceId, String releaseId, CircularProgressButton btnWantlist, CircularProgressButton btnCollection)
    {
        this.inCollection = inCollection;
        this.inWantlist = inWantlist;
        this.instanceId = instanceId;
        this.releaseId = releaseId;
        this.btnWantlist = btnWantlist;
        this.btnCollection = btnCollection;
    }

    public void addToCollection()
    {
        sharedPrefsManager.setFetchNextCollection("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsInteractor.addToCollection(releaseId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (!result.getInstanceId().equals(""))
                            {
                                instanceId = result.getInstanceId();
                                inCollection = true;
                                btnCollection.revertAnimation(() -> btnCollection.setText(context.getString(R.string.remove_from_collection)));
                            }
                            else
                            {
                                toasty.error("Unable to add to Collection");
                                btnCollection.revertAnimation(() -> btnCollection.setText(context.getString(R.string.add_to_collection)));
                            }
                        },
                        error ->
                        {
                            toasty.error("Unable to add to Collection");
                            btnCollection.revertAnimation(() -> btnCollection.setText(context.getString(R.string.add_to_collection)));
                        });
    }

    public void removeFromCollection()
    {
        sharedPrefsManager.setFetchNextCollection("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsInteractor.removeFromCollection(releaseId, instanceId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                inCollection = false;
                                btnCollection.revertAnimation(() -> btnCollection.setText(context.getString(R.string.add_to_collection)));
                            }
                            else
                            {
                                toasty.error("Unable to remove from Collection");
                                btnCollection.revertAnimation(() -> btnCollection.setText(context.getString(R.string.remove_from_collection)));
                            }
                        },
                        error ->
                        {
                            toasty.error("Unable to remove from Collection");
                            btnCollection.revertAnimation(() -> btnCollection.setText(context.getString(R.string.remove_from_collection)));
                        });
    }

    public void addToWantlist()
    {
        sharedPrefsManager.setFetchNextWantlist("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsInteractor.addToWantlist(releaseId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            inWantlist = true;
                            btnWantlist.revertAnimation(() ->
                                    btnWantlist.setText(context.getString(R.string.remove_from_wantlist)));
                        },
                        error ->
                        {
                            toasty.error("Unable to add to Wantlist");
                            btnWantlist.revertAnimation(() -> btnWantlist.setText(context.getString(R.string.add_to_wantlist)));
                        });
    }

    public void removeFromWantlist()
    {
        sharedPrefsManager.setFetchNextWantlist("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsInteractor.removeFromWantlist(releaseId)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                inWantlist = false;
                                btnWantlist.revertAnimation(() -> btnWantlist.setText(context.getString(R.string.add_to_wantlist)));
                            }
                            else
                            {
                                toasty.error("Unable to remove from Wantlist");
                                btnWantlist.revertAnimation(() -> btnWantlist.setText(context.getString(R.string.remove_from_wantlist)));
                            }
                        },
                        error ->
                        {
                            toasty.error("Unable to remove from Wantlist");
                            btnWantlist.revertAnimation(() -> btnWantlist.setText(context.getString(R.string.remove_from_wantlist)));
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
