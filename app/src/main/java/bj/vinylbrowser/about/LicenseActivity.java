package bj.vinylbrowser.about;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import bj.vinylbrowser.R;

/**
 * Created by Josh Laird on 05/05/2017.
 * <p>
 * License page powered by Material About Library by Daniel Stone: https://github.com/daniel-stoneuk/material-about-library
 */
public class LicenseActivity extends AboutActivity
{
    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context)
    {
        MaterialAboutCard discogsBrowser = buildMaterialAboutCard(context, context.getString(R.string.app_name), R.string.license_mit, "2017", "Josh Laird");
        MaterialAboutCard rxJavaRxAndroid = buildMaterialAboutCard(context, "RxJava2/RxAndroid", R.string.license_apache2, "2016-present", "RxJava Contributors");
        MaterialAboutCard retrofit2 = buildMaterialAboutCard(context, "Retrofit2", R.string.license_apache2, "2013", "Square, Inc.");
        MaterialAboutCard rxCache = buildMaterialAboutCard(context, "RxCache", R.string.license_apache2, "2015", "Victor Albertos");
        MaterialAboutCard dagger2 = buildMaterialAboutCard(context, "ButterKnife", R.string.license_apache2, "2012", "The Dagger Authors");
        MaterialAboutCard butterKnife = buildMaterialAboutCard(context, "ButterKnife", R.string.license_apache2, "2013", "Jake Wharton");
        MaterialAboutCard materialDrawer = buildMaterialAboutCard(context, "MaterialDrawer", R.string.license_apache2, "2016", "Mike Penz");
        MaterialAboutCard rxBinding2 = buildMaterialAboutCard(context, "RxBinding2", R.string.license_apache2, "2015", "Jake Wharton");
        MaterialAboutCard leakCanary = buildMaterialAboutCard(context, "RxRelay", R.string.license_apache2, "2015", "Square, Inc.");
        MaterialAboutCard rxRelay = buildMaterialAboutCard(context, "RxRelay", R.string.license_apache2, "2015", "Jake Wharton");
        MaterialAboutCard jSoup = buildMaterialAboutCard(context, "Jsoup", R.string.license_mit, "2009-2017", "Jonathon Hedley");
        MaterialAboutCard circleImageView = buildMaterialAboutCard(context, "CircleImageView", R.string.license_apache2, "2014-2017", "Henning Dodenhof");
        MaterialAboutCard materialDialogs = buildMaterialAboutCard(context, "Material Dialogs", R.string.license_mit, "2014-2017", "Aidan Michael Follestad");
        MaterialAboutCard rxSocialConnect = buildMaterialAboutCard(context, "RxSocialConnect", R.string.license_apache2, "2016", "Boilerplate");
        MaterialAboutCard greenDao = buildMaterialAboutCard(context, "GreenDAO", R.string.license_apache2, "2016", "greenrobot");
        MaterialAboutCard loadingButtonAndroid = buildMaterialAboutCard(context, "LoadingButtonAndroid", R.string.license_mit, "2016", "leandroBorgesFerreira");
        MaterialAboutCard toasty = buildMaterialAboutCard(context, "Toasty", R.string.license_gpl, "2017", "GrenderG");
        MaterialAboutCard materialAboutLibrary = buildMaterialAboutCard(context, "Material About Library", R.string.license_apache2, "2016", "Daniel Stone");
        MaterialAboutCard tapTargetView = buildMaterialAboutCard(context, "TapTargetView", R.string.license_apache2, "2016", "Keepsafe Software Inc.");
        MaterialAboutCard youTubeAndroidPlayer = buildMaterialAboutCard(context, "YouTube Android Player", R.string.license_apache2, "2017", "YouTube");
        MaterialAboutCard conductor = buildMaterialAboutCard(context, "Conductor", R.string.license_apache2, "2016-2017", "BlueLine Labs");
        MaterialAboutCard draggablePanel = buildMaterialAboutCard(context, "Draggable Panel", R.string.license_apache2, "2014", "Pedro Vicente Gómez Sánchez");

        return new MaterialAboutList(discogsBrowser, rxJavaRxAndroid, retrofit2, rxCache, dagger2, butterKnife, materialDrawer, rxBinding2, leakCanary, rxRelay,
                jSoup, circleImageView, materialDialogs, rxSocialConnect, greenDao, loadingButtonAndroid, toasty, materialAboutLibrary, tapTargetView,
                youTubeAndroidPlayer, conductor, draggablePanel);
    }

    /**
     * Convenience builder for the {@link MaterialAboutCard}s.
     *
     * @param c       Activity context
     * @param text    Library name
     * @param license Library license
     * @param year    Library year copyright
     * @param author  Library author
     * @return Built MaterialAboutCard
     */
    private MaterialAboutCard buildMaterialAboutCard(Context c, String text, int license, String year, String author)
    {
        MaterialAboutCard.Builder builder = new MaterialAboutCard.Builder();
        builder.addItem(new MaterialAboutActionItem.Builder()
                .text(text)
                .subText(String.format(getString(license), year, author))
                .icon(new IconDrawable(c, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());
        return builder.build();
    }

    @Nullable
    @Override
    protected CharSequence getActivityTitle()
    {
        return "Licenses";
    }

    public static Intent createIntent(Context context)
    {
        return new Intent(context, LicenseActivity.class);
    }
}
