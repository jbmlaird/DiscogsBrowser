package bj.rxjavaexperimentation.detailedview;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public interface DetailedContract
{
    interface View
    {
    }

    interface Presenter
    {
        void fetchDetailedInformation(String type, String id);
    }
}
