package bj.discogsbrowser.epoxy.common;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyController;

/**
 * Created by Josh Laird on 25/04/2017.
 */
public abstract class BaseController extends EpoxyController
{
    public String title = "";
    public String subtitle = "";
    public String imageUrl = "";

    @AutoModel protected HeaderModel_ header;

    public void setTitle(String title)
    {
        this.title = title;
    }
}
