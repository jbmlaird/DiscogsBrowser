package bj.discogsbrowser.model.artist;

import bj.discogsbrowser.artist.ArtistController;
import bj.discogsbrowser.epoxy.artist.UrlModel;

/**
 * Created by Josh Laird on 21/04/2017.
 * <p>
 * To ease {@link ArtistController}'s creation of {@link UrlModel}.
 */
public class ArtistWantedUrl
{
    private String url;
    private String displayText;
    private String hexCode;
    private String fontAwesomeString;

    public ArtistWantedUrl(String url, String displayText, String hexCode, String fontAwesomeString)
    {
        this.url = url;
        this.displayText = displayText;
        this.hexCode = hexCode;
        this.fontAwesomeString = fontAwesomeString;
    }

    public String getUrl()
    {
        return url;
    }

    public String getDisplayText()
    {
        return displayText;
    }

    public String getHexCode()
    {
        return hexCode;
    }

    public String getFontAwesomeString()
    {
        return fontAwesomeString;
    }
}