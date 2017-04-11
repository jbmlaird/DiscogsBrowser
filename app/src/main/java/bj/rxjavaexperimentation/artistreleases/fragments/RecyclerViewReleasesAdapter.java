package bj.rxjavaexperimentation.artistreleases.fragments;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 10/04/2017.
 */

public class RecyclerViewReleasesAdapter extends RecyclerView.Adapter<RecyclerViewReleasesAdapter.MyViewHolder>
{
    private List<ArtistRelease> remixes = new ArrayList<>();
    private Context context;

    public void setRemixes(List<ArtistRelease> remixes)
    {
        this.remixes = remixes;
    }

    public RecyclerViewReleasesAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discogs_result, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.tvTitle.setText(remixes.get(holder.getAdapterPosition()).getTitle());
        holder.tvType.setText(remixes.get(holder.getAdapterPosition()).getArtist());
        Glide.with(context)
                .load(remixes.get(holder.getAdapterPosition()).getThumb())
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .crossFade()
                .into(holder.ivImage);
        // Set in onBind rather than via ButterKnife's @OnClick to prevent UI lock
        holder.lytCard.setOnClickListener(v ->
        {

        });
    }

    @Override
    public int getItemCount()
    {
        return remixes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.lytCard) CardView lytCard;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvType) TextView tvType;
        @BindView(R.id.ivImage) ImageView ivImage;

        MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
