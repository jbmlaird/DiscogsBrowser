package bj.vinylbrowser.utils;

import bj.vinylbrowser.wrappers.DateUtilsWrapper;
import bj.vinylbrowser.wrappers.SimpleDateFormatWrapper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 14/05/2017.
 */
@Module
public class UtilsModule
{
    @Provides
    protected ImageViewAnimator provideImageViewAnimator()
    {
        return new ImageViewAnimator();
    }

    @Provides
    protected ArtistsBeautifier provideArtistsBeautifier()
    {
        return new ArtistsBeautifier();
    }

    @Provides
    protected DiscogsScraper provideDiscogsScraper()
    {
        return new DiscogsScraper();
    }

    @Provides
    protected DateFormatter provideDateFormatter(DateUtilsWrapper dateUtilsWrapper, SimpleDateFormatWrapper simpleDateFormatWrapper)
    {
        return new DateFormatter(dateUtilsWrapper, simpleDateFormatWrapper);
    }
}