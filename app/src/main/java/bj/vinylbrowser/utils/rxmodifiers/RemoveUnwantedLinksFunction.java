package bj.vinylbrowser.utils.rxmodifiers;

import java.util.ArrayList;

import bj.vinylbrowser.model.artist.ArtistResult;
import bj.vinylbrowser.model.artist.ArtistWantedUrl;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 11/05/2017.
 * <p>
 * Filters out links returned from the Discogs Artists API endpoint to links that I want.
 * Also removes duplicates.
 */
public class RemoveUnwantedLinksFunction implements Function<ArtistResult, ArtistResult>
{
    @Override
    public ArtistResult apply(@NonNull ArtistResult artistResult) throws Exception
    {
        if (artistResult.getUrls() == null)
            return artistResult;
        boolean wikipedia, youtube, facebook, spotify, twitter, soundcloud;
        wikipedia = youtube = facebook = spotify = twitter = soundcloud = false;
        ArrayList<ArtistWantedUrl> artistWantedUrls = new ArrayList<>();
        for (String string : artistResult.getUrls())
        {
            if (string.contains("spotify") && !spotify)
            {
                artistWantedUrls.add(new ArtistWantedUrl(string, "Listen on Spotify", "#6ae368", "{fa-spotify}"));
                spotify = true;
            }
            else if (string.contains("wikipedia") && !wikipedia)
            {
                artistWantedUrls.add(new ArtistWantedUrl(string, "Learn more on Wikipedia", "#000000", "{fa-wikipedia-w}"));
                wikipedia = true;
            }
            else if (string.contains("facebook") && !facebook)
            {
                artistWantedUrls.add(new ArtistWantedUrl(string, "Check out Facebook", "#3b5998", "{fa-facebook}"));
                facebook = true;
            }
            else if (string.contains("twitter") && !twitter)
            {
                artistWantedUrls.add(new ArtistWantedUrl(string, "Follow on Twitter", "#4099FF", "{fa-twitter}"));
                twitter = true;
            }
            else if (string.contains("youtube") && !youtube)
            {
                artistWantedUrls.add(new ArtistWantedUrl(string, "Watch on YouTube", "#bb0000", "{fa-youtube-play}"));
                youtube = true;
            }
            else if (string.contains("soundcloud") && !soundcloud)
            {
                artistWantedUrls.add(new ArtistWantedUrl(string, "Listen on SoundCloud", "#ff7700", "{fa-soundcloud}"));
                soundcloud = true;
            }
        }
        artistResult.setArtistWantedUrls(artistWantedUrls);
        return artistResult;
    }
}
