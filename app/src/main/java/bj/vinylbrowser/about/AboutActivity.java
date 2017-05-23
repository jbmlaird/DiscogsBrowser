package bj.vinylbrowser.about;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import bj.vinylbrowser.R;

/**
 * Created by Josh Laird on 04/05/2017.
 * <p>
 * About page powered by Material About Library by Daniel Stone: https://github.com/daniel-stoneuk/material-about-library
 */
public class AboutActivity extends MaterialAboutActivity
{
    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context)
    {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();

        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .icon(R.drawable.ic_app)
                .build());

        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Licenses")
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_file_text).sizeDp(18))
                .setOnClickAction(() ->
                        context.startActivity(LicenseActivity.createIntent(context)))
                .build());

        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title("Author");

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Josh Laird")
                .subText("London, UK")
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_user).sizeDp(18))
                .build());

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Fork on Github")
                .icon(new IconDrawable(context, FontAwesomeIcons.fa_github).sizeDp(18))
                .setOnClickAction(() ->
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/jbmlaird"));
                    startActivity(intent);
                })
                .build());

        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    protected CharSequence getActivityTitle()
    {
        return getString(R.string.mal_title_about);
    }

    public static Intent createIntent(Context context)
    {
        return new Intent(context, AboutActivity.class);
    }
}
