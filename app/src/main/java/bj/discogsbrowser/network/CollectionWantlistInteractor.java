package bj.discogsbrowser.network;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import bj.discogsbrowser.epoxy.release.CollectionWantlistModel;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 10/05/2017.
 */
public class CollectionWantlistInteractor
{
    private Context context;
    private CollectionWantlistService discogsService;
    private SharedPrefsManager sharedPrefsManager;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public CollectionWantlistInteractor(Context context, Retrofit retrofit, SharedPrefsManager sharedPrefsManager, MySchedulerProvider mySchedulerProvider)
    {
        this.context = context;
        this.discogsService = retrofit.create(CollectionWantlistService.class);
        this.sharedPrefsManager = sharedPrefsManager;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    public void addToCollection(CollectionWantlistModel model, String releaseId, CircularProgressButton btnCollection)
    {
        sharedPrefsManager.setFetchNextCollection("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsService.addToCollection(sharedPrefsManager.getUsername(), releaseId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.getInstanceId() != null)
                            {
                                model.instanceId = result.getInstanceId();
                                model.inCollection = true;
                                btnCollection.revertAnimation(() -> btnCollection.setText("Remove from Collection"));
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
    }

    public void removeFromCollection(CollectionWantlistModel model, String releaseId, String instanceId, CircularProgressButton btnCollection)
    {
        sharedPrefsManager.setFetchNextCollection("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsService.removeFromCollection(sharedPrefsManager.getUsername(), releaseId, instanceId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                model.inCollection = false;
                                btnCollection.revertAnimation(() -> btnCollection.setText("Add to Collection"));
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

    public void addToWantlist(CollectionWantlistModel model, String releaseId, CircularProgressButton btnWantlist)
    {
        sharedPrefsManager.setFetchNextWantlist("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsService.addToWantlist(sharedPrefsManager.getUsername(), releaseId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            model.inWantlist = true;
                            btnWantlist.revertAnimation(() -> btnWantlist.setText("Remove from Wantlist"));
                        },
                        error ->
                        {
                            Toasty.error(context, "Unable to add to Wantlist", Toast.LENGTH_SHORT, true).show();
                            btnWantlist.revertAnimation();
                        });
    }

    public void removeFromWantlist(CollectionWantlistModel model, String releaseId, CircularProgressButton btnWantlist)
    {
        sharedPrefsManager.setFetchNextWantlist("yes");
        sharedPrefsManager.setfetchNextUserDetails("yes");
        discogsService.removeFromWantlist(sharedPrefsManager.getUsername(), releaseId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.isSuccessful())
                            {
                                model.inWantlist = false;
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
}
