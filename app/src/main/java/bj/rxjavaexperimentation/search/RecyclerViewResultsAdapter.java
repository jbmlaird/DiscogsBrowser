package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.ResultModel;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.release.Image;
import bj.rxjavaexperimentation.model.release.Release;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josh Laird on 19/02/2017.
 */
public class RecyclerViewResultsAdapter extends RecyclerView.Adapter<RecyclerViewResultsAdapter.MyViewHolder>
{
    private static final String TAG = "ResultsAdapter";
    private final Context mContext;
    private SearchPresenter searchPresenter;
    private ArrayList<ResultModel> searchResults = new ArrayList<>();
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public RecyclerViewResultsAdapter(SearchPresenter searchPresenter, Context context, ArrayList<ResultModel> searchResults)
    {
        this.searchPresenter = searchPresenter;
        this.mContext = context;
        this.searchResults = searchResults;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_discogs_result, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        if (searchResults.get(holder.getAdapterPosition()) instanceof Release)
        {
            Release release = (Release) searchResults.get(holder.getAdapterPosition());
            fillDetails(holder, release.getTitle(), release.getArtists().get(0).getName(), release.getImages());
        }
        else if (searchResults.get(holder.getAdapterPosition()) instanceof ArtistResult)
        {
            ArtistResult artistResult = (ArtistResult) searchResults.get(holder.getAdapterPosition());
            try
            {
                fillDetails(holder, artistResult.getNamevariations().get(0), "", artistResult.getImages());
            }
            catch (IndexOutOfBoundsException e)
            {
                Log.e(TAG, "test");
                fillDetails(holder, artistResult.getUri().substring(artistResult.getResourceUrl().length()), "", artistResult.getImages());
            }
        }
    }

    /**
     * Fill the list with relevant details returned from the search.
     *
     * @param holder    Element in the list.
     * @param header    Title of element in the list.
     * @param subHeader Subheader of element in the list.
     * @param uri       List of image URIs.
     */
    private void fillDetails(MyViewHolder holder, String header, String subHeader, List<Image> uri)
    {
        holder.tvTitle.setText(header);
        holder.tvArtist.setText(subHeader);
        if (uri != null)
            try
            {
                imageLoader.loadImage(uri.get(0).getUri(), new SimpleImageLoadingListener()
                {
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason)
                    {
                        super.onLoadingFailed(imageUri, view, failReason);
                        holder.pbImage.setVisibility(View.GONE);
                        // Display error image
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                    {
                        Log.wtf(TAG, "LoadingComplete");
                        holder.ivImage.setImageBitmap(loadedImage);
                        holder.pbImage.setVisibility(View.GONE);
                    }
                });
            }
            catch (NullPointerException e)
            {
                holder.pbImage.setVisibility(View.GONE);
                // Display error message
            }
        else
            // Display stock image if there are no images for that result
            holder.ivImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_vinyl));
    }

    @Override
    public int getItemCount()
    {
        return searchResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.lytCard) LinearLayout lytCard;
        @BindView(R.id.pbImage) ProgressBar pbImage;
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvArtist) TextView tvArtist;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.lytCard)
        public void expandResult()
        {
            searchPresenter.goToResult(ivImage);
        }
    }
}
