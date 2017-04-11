package bj.rxjavaexperimentation.detailedview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.artist.Member;

/**
 * Created by Josh Laird on 10/04/2017.
 */
public class DetailedBodyModelPresenter
{
    private Context context;
    private LayoutInflater mInflater;
    private DetailedContract.View view;

    @Inject
    DetailedBodyModelPresenter(Context context, DetailedContract.View view)
    {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.view = view;
    }

    public void displayMembers(RelativeLayout lytMembersContainer, LinearLayout lytMembers, List<Member> members)
    {
        if (members != null)
        {
            if (members.size() > 0)
                for (Member member : members)
                {
                    View memberView = mInflater.inflate(R.layout.item_member, null);
                    ((TextView) memberView.findViewById(R.id.tvName)).setText(member.getName());
                    memberView.findViewById(R.id.lytMember).setOnClickListener(v ->
                            view.showMemberDetails(member.getName(), member.getId()));
                    lytMembers.addView(memberView);
                }
        }
        else
            lytMembersContainer.setVisibility(View.GONE);
    }

    public void displaySocialLinks(LinearLayout lytLinksContainer, List<String> links, LinearLayout lytLinks)
    {
        if (links != null)
        {
            if (links.size() > 0)
            {
                boolean wikipedia, youtube, facebook, spotify, twitter, soundcloud;
                wikipedia = youtube = facebook = spotify = twitter = soundcloud = false;
                for (String string : links)
                {
                    if (string.contains("spotify") && !spotify)
                    {
                        spotify = extractUrl(string, lytLinks, "spotify");
                    }
                    else if (string.contains("wikipedia") && !wikipedia)
                    {
                        wikipedia = extractUrl(string, lytLinks, "wikipedia");
                    }
                    else if (string.contains("facebook") && !facebook)
                    {
                        facebook = extractUrl(string, lytLinks, "facebook");
                    }
                    else if (string.contains("twitter") && !twitter)
                    {
                        twitter = extractUrl(string, lytLinks, "twitter");
                    }
                    else if (string.contains("youtube") && !youtube)
                    {
                        youtube = extractUrl(string, lytLinks, "youtube");
                    }
                    else if (string.contains("soundcloud") && !soundcloud)
                    {
                        wikipedia = extractUrl(string, lytLinks, "soundcloud");
                    }
                }
            }
        }
        else
        {
            lytLinksContainer.setVisibility(View.GONE);
        }
    }

    private boolean extractUrl(String string, LinearLayout lytLinks, String site)
    {
        View memberView;
        switch (site)
        {
            case "spotify":
                memberView = mInflater.inflate(R.layout.item_artist_link_spotify, null);
                break;
            case "youtube":
                memberView = mInflater.inflate(R.layout.item_artist_link_youtube, null);
                break;
            case "wikipedia":
                memberView = mInflater.inflate(R.layout.item_artist_link_wikipedia, null);
                break;
            case "twitter":
                memberView = mInflater.inflate(R.layout.item_artist_link_twitter, null);
                break;
            case "soundcloud":
                memberView = mInflater.inflate(R.layout.item_artist_link_soundcloud, null);
                break;
            case "facebook":
                memberView = mInflater.inflate(R.layout.item_artist_link_facebook, null);
                break;
            default:
                // This will never get called
                memberView = null;
        }
        memberView.findViewById(R.id.lytLink).setOnClickListener(v ->
                new FinestWebView.Builder(context).show(string));
        lytLinks.addView(memberView);
        return true;
    }
}
