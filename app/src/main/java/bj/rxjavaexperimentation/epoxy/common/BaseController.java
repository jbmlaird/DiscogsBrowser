package bj.rxjavaexperimentation.epoxy.common;

import com.airbnb.epoxy.EpoxyController;

/**
 * Created by Josh Laird on 25/04/2017.
 */
public abstract class BaseController extends EpoxyController
{
    public String title = "";
    public String subtitle = "";
    public String imageUrl = "";

    // Can't use this as ProGuard causes issues with AutoModels :/
    // public @AutoModel HeaderModel_ header;

    public void setTitle(String title)
    {
        this.title = title;
    }
}
