package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.search.SearchResult;
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
    private ArrayList<SearchResult> searchResults = new ArrayList<>();

    public RecyclerViewResultsAdapter(SearchPresenter searchPresenter, Context context)
    {
        this.searchPresenter = searchPresenter;
        this.mContext = context;
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
        fillDetails(holder, searchResults.get(holder.getAdapterPosition()));
    }

    /**
     * Fill the list with relevant details returned from the search.
     *
     * @param holder       Element in the list.
     * @param searchResult Search result.
     */
    private void fillDetails(MyViewHolder holder, SearchResult searchResult)
    {
        holder.tvTitle.setText(searchResult.getTitle());
        holder.tvArtist.setText(searchResult.getType());
        if (!searchResult.getThumb().equals(""))
            Glide.with(mContext)
                    .load(searchResult.getThumb())
                    .centerCrop()
                    .placeholder(R.drawable.ic_vinyl)
                    .crossFade()
                    .into(holder.ivImage);
        else
            // Display stock image if there are no images for that result
            holder.ivImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_vinyl));
    }

    @Override
    public int getItemCount()
    {
        return searchResults.size();
    }

    public void setResults(ArrayList<SearchResult> results)
    {
        this.searchResults = results;
    }

    public ArrayList<SearchResult> getResults()
    {
        return searchResults;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.lytCard) LinearLayout lytCard;
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
