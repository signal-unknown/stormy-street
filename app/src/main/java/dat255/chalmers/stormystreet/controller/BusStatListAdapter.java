package dat255.chalmers.stormystreet.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.view.StatCardData;
import dat255.chalmers.stormystreet.view.StatCard;

/**
 * This implementation handles bus info cards that will be shown in a RecyclerView (list).
 *
 * @author Alexander Håkansson
 */
public class BusStatListAdapter extends RecyclerView.Adapter<BusStatListAdapter.ViewHolder> {

    private final List<StatCardData> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public StatCard card;
        public ViewHolder(StatCard statCard) {
            super(statCard);
            card = statCard;
        }
    }

    public BusStatListAdapter(List<StatCardData> stats) {
        this.dataset = stats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StatCard card = (StatCard) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_busstatcard, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StatCardData stat = dataset.get(position);
        holder.card.setIcon(stat.getIcon());
        holder.card.setTopText(stat.getValue());
        holder.card.setBottomText(stat.getSuffix());
        if (position == 0) {
            holder.card.setLarge();
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
