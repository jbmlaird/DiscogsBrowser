package bj.discogsbrowser.about;

import android.content.Context;
import android.content.Intent;
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
import com.thefinestartist.finestwebview.FinestWebView;

import bj.discogsbrowser.R;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 04/05/2017.
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
                .setOnClickAction(() -> new FinestWebView.Builder(context).show("http://github.com/jbmlaird"))
                .build());

        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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
