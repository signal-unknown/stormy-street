package dat255.chalmers.stormystreet.controller;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.view.BusStat;
import dat255.chalmers.stormystreet.view.BusStatCard;
import dat255.chalmers.stormystreet.view.ScoreCard;

/**
 * @author Alexander Håkansson
 * @since 2015-09-24
 */
public class BusStatListAdapter extends RecyclerView.Adapter<BusStatListAdapter.ViewHolder> {

    private final List<BusStat> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public BusStatCard card;
        public ViewHolder(BusStatCard statCard) {
            super(statCard);
            card = statCard;
        }
    }

    public BusStatListAdapter(List<BusStat> stats) {
        this.dataset = stats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BusStatCard card = (BusStatCard) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_busstatcard, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusStat stat = dataset.get(position);
        holder.card.setIcon(stat.getIcon());
        holder.card.setTopText(stat.getValue());
        holder.card.setBottomText(stat.getSuffix());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
