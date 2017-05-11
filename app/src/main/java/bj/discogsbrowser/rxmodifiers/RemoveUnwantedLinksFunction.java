package bj.discogsbrowser.rxmodifiers;

import java.util.ArrayList;

import javax.inject.Inject;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.utils.WantedUrl;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 11/05/2017.
 */
public class RemoveUnwantedLinksFunction implements Function<ArtistResult, ArtistResult>
{
    @Inject
    public RemoveUnwantedLinksFunction()
    {
    }

    @Override
    public ArtistResult apply(@NonNull ArtistResult artistResult) throws Exception
    {
        if (artistResult.getUrls() == null)
            return artistResult;
        boolean wikipedia, youtube, facebook, spotify, twitter, soundcloud;
        wikipedia = youtube = facebook = spotify = twitter = soundcloud = false;
        ArrayList<WantedUrl> wantedUrls = new ArrayList<>();
        for (String string : artistResult.getUrls())
        {
            if (string.contains("spotify") && !spotify)
            {
                wantedUrls.add(new WantedUrl(string, "Listen on Spotify", "#6ae368", "{fa-spotify}"));
                spotify = true;
            }
            else if (string.contains("wikipedia") && !wikipedia)
            {
                wantedUrls.add(new WantedUrl(string, "Learn more on Wikipedia", "#000000", "{fa-wikipedia-w}"));
                wikipedia = true;
            }
            else if (string.contains("facebook") && !facebook)
            {
                wantedUrls.add(new WantedUrl(string, "Check out Facebook", "#3b5998", "{fa-facebook}"));
                facebook = true;
            }
            else if (string.contains("twitter") && !twitter)
            {
                wantedUrls.add(new WantedUrl(string, "Follow on Twitter", "#4099FF", "{fa-twitter}"));
                twitter = true;
            }
            else if (string.contains("youtube") && !youtube)
            {
                wantedUrls.add(new WantedUrl(string, "Watch on YouTube", "#bb0000", "{fa-youtube-play}"));
                youtube = true;
            }
            else if (string.contains("soundcloud") && !soundcloud)
            {
                wantedUrls.add(new WantedUrl(string, "Listen on SoundCloud", "#ff7700", "{fa-soundcloud}"));
                soundcloud = true;
            }
        }
        artistResult.setWantedUrls(wantedUrls);
        return artistResult;
    }
}
