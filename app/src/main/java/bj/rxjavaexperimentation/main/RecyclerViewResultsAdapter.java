package bj.rxjavaexperimentation.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.discogs.gson.release.Release;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 19/02/2017.
 */
public class RecyclerViewResultsAdapter extends RecyclerView.Adapter<RecyclerViewResultsAdapter.MyViewHolder>
{
    private static final String TAG = "RecyclerViewResultsAdapter";
    private final Context mContext;
    private List<Release> searchResults = new ArrayList<>();
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public RecyclerViewResultsAdapter(Context context, ArrayList<Release> searchResults)
    {
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
        holder.tvTitle.setText(searchResults.get(holder.getAdapterPosition()).getTitle());
        holder.tvArtist.setText(searchResults.get(holder.getAdapterPosition()).getArtists().get(0).getName());
        try
        {
            imageLoader.loadImage(searchResults.get(holder.getAdapterPosition()).getImages().get(0).getUri(), new SimpleImageLoadingListener()
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
    }

    @Override
    public int getItemCount()
    {
        return searchResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.pbImage) ProgressBar pbImage;
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvArtist) TextView tvArtist;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
