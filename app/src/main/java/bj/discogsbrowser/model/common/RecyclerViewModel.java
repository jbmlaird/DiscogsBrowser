package bj.discogsbrowser.model.common;

/**
 * Created by Josh Laird on 19/04/2017.
 */

public interface RecyclerViewModel
{
    String getTitle();

    String getSubtitle();

    String getThumb();

    String getType();

    // Has to be a String as Order IDs have hyphens
    String getId();
}
