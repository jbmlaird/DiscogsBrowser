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
import bj.rxjavaexperimentation.artistreleases.ArtistReleasesPresenter;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 10/04/2017.
 */

public class ArtistReleasesAdapter extends RecyclerView.Adapter<ArtistReleasesAdapter.MyViewHolder>
{
    private List<ArtistRelease> releases = new ArrayList<>();
    private ArtistReleasesPresenter presenter;
    private Context context;

    public ArtistReleasesAdapter(ArtistReleasesPresenter presenter, Context context)
    {
        this.presenter = presenter;
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
        holder.tvTitle.setText(releases.get(holder.getAdapterPosition()).getTitle());
        holder.tvSubtitle.setText(releases.get(holder.getAdapterPosition()).getArtist());
        Glide.with(context)
                .load(releases.get(holder.getAdapterPosition()).getThumb())
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .into(holder.ivImage);
        // Set in onBind rather than via ButterKnife's @OnClick to prevent UI lock
        holder.lytCard.setOnClickListener(v ->
                presenter.launchDetailedActivity(releases.get(holder.getAdapterPosition()).getType(), releases.get(holder.getAdapterPosition()).getTitle(), releases.get(holder.getAdapterPosition()).getId()));
    }

    public void setReleases(List<ArtistRelease> releases)
    {
        this.releases = releases;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return releases.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.lytCard) CardView lytCard;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvSubtitle) TextView tvSubtitle;
        @BindView(R.id.ivImage) ImageView ivImage;

        MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
