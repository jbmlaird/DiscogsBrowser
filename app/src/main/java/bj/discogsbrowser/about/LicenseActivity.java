package bj.discogsbrowser.about;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import bj.discogsbrowser.R;

/**
 * Created by Josh Laird on 05/05/2017.
 */

public class LicenseActivity extends AboutActivity
{
    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context)
    {
        //TODO: Move these to a convenience builder
        MaterialAboutCard.Builder license1 = new MaterialAboutCard.Builder();
        license1.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.app_name)
                .subText(String.format(getString(R.string.license_mit), "2017", "Josh Laird"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license2 = new MaterialAboutCard.Builder();
        license2.addItem(new MaterialAboutActionItem.Builder()
                .text("RxJava2/RxAndroid")
                .subText(String.format(getString(R.string.license_apache2), "2016-present", "RxJava Contributors"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license3 = new MaterialAboutCard.Builder();
        license3.addItem(new MaterialAboutActionItem.Builder()
                .text("Retrofit2")
                .subText(String.format(getString(R.string.license_apache2), "2013", "Square, Inc."))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license4 = new MaterialAboutCard.Builder();
        license4.addItem(new MaterialAboutActionItem.Builder()
                .text("RxCache")
                .subText(String.format(getString(R.string.license_apache2), "2015", "Victor Albertos"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license5 = new MaterialAboutCard.Builder();
        license5.addItem(new MaterialAboutActionItem.Builder()
                .text("Dagger2")
                .subText(String.format(getString(R.string.license_apache2), "2012", "The Dagger Authors"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license6 = new MaterialAboutCard.Builder();
        license6.addItem(new MaterialAboutActionItem.Builder()
                .text("ButterKnife")
                .subText(String.format(getString(R.string.license_apache2), "2013", "Jake Wharton"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license7 = new MaterialAboutCard.Builder();
        license7.addItem(new MaterialAboutActionItem.Builder()
                .text("MaterialDrawer")
                .subText(String.format(getString(R.string.license_apache2), "2016", "Mike Penz"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license8 = new MaterialAboutCard.Builder();
        license8.addItem(new MaterialAboutActionItem.Builder()
                .text("RxBinding2")
                .subText(String.format(getString(R.string.license_apache2), "2015", "Jake Wharton"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license9 = new MaterialAboutCard.Builder();
        license9.addItem(new MaterialAboutActionItem.Builder()
                .text("LeakCanary")
                .subText(String.format(getString(R.string.license_apache2), "2015", "Square, Inc."))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license10 = new MaterialAboutCard.Builder();
        license10.addItem(new MaterialAboutActionItem.Builder()
                .text("FinestWebView")
                .subText(String.format(getString(R.string.license_mit), "2013", "The Finest Artist"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license11 = new MaterialAboutCard.Builder();
        license11.addItem(new MaterialAboutActionItem.Builder()
                .text("RxRelay")
                .subText(String.format(getString(R.string.license_apache2), "2015", "Jake Wharton"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license12 = new MaterialAboutCard.Builder();
        license12.addItem(new MaterialAboutActionItem.Builder()
                .text("Jsoup")
                .subText(String.format(getString(R.string.license_mit), "2009-2017", "Jonathon Hedley"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license13 = new MaterialAboutCard.Builder();
        license13.addItem(new MaterialAboutActionItem.Builder()
                .text("CircleImageView")
                .subText(String.format(getString(R.string.license_apache2), "2014-2017", "Henning Dodenhof"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license14 = new MaterialAboutCard.Builder();
        license14.addItem(new MaterialAboutActionItem.Builder()
                .text("Material Dialogs")
                .subText(String.format(getString(R.string.license_mit), "2014-2017", "Aidan Michael Follestad"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license15 = new MaterialAboutCard.Builder();
        license15.addItem(new MaterialAboutActionItem.Builder()
                .text("RxSocialConnect")
                .subText(String.format(getString(R.string.license_apache2), "2016", "Fuck Boilerplate"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license16 = new MaterialAboutCard.Builder();
        license16.addItem(new MaterialAboutActionItem.Builder()
                .text("GreenDAO")
                .subText(String.format(getString(R.string.license_apache2), "2016", "greenrobot"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license17 = new MaterialAboutCard.Builder();
        license17.addItem(new MaterialAboutActionItem.Builder()
                .text("LoadingButtonAndroid")
                .subText(String.format(getString(R.string.license_mit), "2016", "leandroBorgesFerreira"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license18 = new MaterialAboutCard.Builder();
        license18.addItem(new MaterialAboutActionItem.Builder()
                .text("Toasty")
                .subText(String.format(getString(R.string.license_gpl), "2017", "leandroBorgesFerreira"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license19 = new MaterialAboutCard.Builder();
        license19.addItem(new MaterialAboutActionItem.Builder()
                .text("Material About Library")
                .subText(String.format(getString(R.string.license_apache2), "2016", "daniel-stoneuk"))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        MaterialAboutCard.Builder license20 = new MaterialAboutCard.Builder();
        license20.addItem(new MaterialAboutActionItem.Builder()
                .text("TapTargetView")
                .subText(String.format(getString(R.string.license_apache2), "2016", "Keepsafe Software Inc."))
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_book).sizeDp(18))
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build());

        return new MaterialAboutList(license1.build(), license2.build(), license3.build(), license4.build(), license5.build(), license6.build(), license7.build(), license8.build(), license9.build(), license10.build(),
                license11.build(), license12.build(), license13.build(), license14.build(), license15.build(), license16.build(), license17.build(), license18.build(), license19.build(), license20.build());
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
