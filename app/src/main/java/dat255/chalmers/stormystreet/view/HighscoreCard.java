package dat255.chalmers.stormystreet.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import dat255.chalmers.stormystreet.R;

/**
 * @author Kevin Hoogendijk
 * @since 2015-10-14
 */
public class HighscoreCard extends CardView {
    private TextView placeView, nameView, scoreView;

    public HighscoreCard(Context context) {
        super(context);
        init();
    }

    public HighscoreCard(Context context, AttributeSet attr){
        super(context, attr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.cardview_highscorecard, this);
        placeView = (TextView) findViewById(R.id.highscorecard_place);
        nameView = (TextView) findViewById(R.id.highscorecard_username);
        scoreView = (TextView) findViewById(R.id.highscorecard_score);
    }

    public void setPlace(int place){
        if(place != 0){
            this.placeView.setText(Integer.toString(place));
            this.placeView.setVisibility(View.VISIBLE);
        }
    }

    public void setName(String name){
        if(name != null){
            this.nameView.setText(name);
            this.nameView.setVisibility(View.VISIBLE);
        }
    }

    public void setScore(int score){
        if(score != 0){
            this.scoreView.setText(Integer.toString(score));
            this.scoreView.setVisibility(View.VISIBLE);
        }
    }

    public void setLarge(){
        setPadding(16, 16, 16, 16);
        placeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
    }
}
