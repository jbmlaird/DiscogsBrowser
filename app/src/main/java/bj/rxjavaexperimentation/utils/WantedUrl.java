package bj.rxjavaexperimentation.utils;

import bj.rxjavaexperimentation.artist.ArtistController;
import bj.rxjavaexperimentation.epoxy.artist.UrlModel;

/**
 * Created by Josh Laird on 21/04/2017.
 * <p>
 * To ease {@link ArtistController}'s creation of {@link UrlModel}.
 */
public class WantedUrl
{
    private String url;
    private String displayText;
    private String hexCode;
    private String fontAwesomeString;

    public WantedUrl(String url, String displayText, String hexCode, String fontAwesomeString)
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
