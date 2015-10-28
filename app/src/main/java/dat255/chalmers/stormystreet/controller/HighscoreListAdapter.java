package dat255.chalmers.stormystreet.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.view.HighscoreCard;
import dat255.chalmers.stormystreet.view.HighscoreCardData;

/**
 * This list adapter implementation handles the users facebook friends in a RecyclerView
 *
 * @author Kevin Hoogendijk
 * @see RecyclerView
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

    /**
     * Fills the card with data if it is active
     * @param holder A viewholder that holds the card
     * @param position the what card is activated
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HighscoreCardData data = dataset.get(position);
        if(data != null) {
            holder.card.setPlace(data.getPlace());
            holder.card.setName(data.getName());
            holder.card.setScore(data.getScore());
            holder.card.setFacebookId(data.getId());
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
