package dat255.chalmers.stormystreet.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.view.HighscoreCard;
import dat255.chalmers.stormystreet.view.HighscoreCardData;

/**
 * @author Kevin Hoogendijk
 * @since 2015-10-14
 */
public class HighscoreListAdapter extends RecyclerView.Adapter<HighscoreListAdapter.ViewHolder>{

    private List<HighscoreCardData> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public HighscoreCard card;
        public ViewHolder(HighscoreCard c){
            super(c);
            card = c;
        }
    }

    public HighscoreListAdapter(List<HighscoreCardData> dataset){
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HighscoreCard card = (HighscoreCard) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_highscorecard, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HighscoreCardData data = dataset.get(position);
        holder.card.setPlace(data.getPlace());
        holder.card.setName(data.getName());
        holder.card.setScore(data.getScore());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
