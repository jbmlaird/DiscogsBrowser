package bj.rxjavaexperimentation.singlelist;

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

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.common.RecyclerViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 19/04/2017.
 */
@Singleton
public class SingleListAdapter extends RecyclerView.Adapter<SingleListAdapter.SingleListViewHolder>
{
    private List<? extends RecyclerViewModel> items = new ArrayList<>();
    private Context context;
    private SingleListContract.View view;

    @Inject
    public SingleListAdapter(SingleListContract.View view)
    {
        this.context = view.getActivityContext();
        this.view = view;
    }

    @Override
    public SingleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discogs_result, parent, false);
        return new SingleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleListViewHolder holder, int position)
    {
        holder.tvTitle.setText(items.get(holder.getAdapterPosition()).getTitle());
        holder.tvSubtitle.setText(items.get(holder.getAdapterPosition()).getSubtitle());
        Glide.with(context)
                .load(items.get(holder.getAdapterPosition()).getThumb())
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .into(holder.ivImage);
        // Set in onBind rather than via ButterKnife's @OnClick to prevent UI lock
        holder.lytCard.setOnClickListener(v ->
        {
            RecyclerViewModel item = items.get(holder.getAdapterPosition());
            switch (item.getType())
            {
                case "listing":
                    view.displayListing(item.getId(), item.getTitle(), item.getSubtitle(), "");
                    break;
                case "order":
                    view.displayOrder(item.getId());
                    break;
                case "release":
                    view.launchDetailedActivity(item.getType(), item.getTitle(), item.getId());
                    break;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public void setItems(List<? extends RecyclerViewModel> wants)
    {
        this.items = wants;
    }

    public class SingleListViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.lytCard) CardView lytCard;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvSubtitle) TextView tvSubtitle;
        @BindView(R.id.ivImage) ImageView ivImage;

        public SingleListViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
