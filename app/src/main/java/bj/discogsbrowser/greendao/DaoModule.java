package bj.discogsbrowser.greendao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.greendao.DaoMaster;
import bj.discogsbrowser.greendao.DaoSession;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 16/05/2017.
 */
@Module
public class DaoModule
{
    @Provides
    @Singleton
    protected DaoSession providesDaoSession(Context applicationContext)
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(applicationContext, "search-db");
        // Use when changing schema
        // helper.onUpgrade(helper.getWritableDatabase(), 7, 8);
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    @Provides
    @Singleton
    protected DaoManager providesDaoInteractor(DaoSession daoSession)
    {
        return new DaoManager(daoSession);
    }
}
