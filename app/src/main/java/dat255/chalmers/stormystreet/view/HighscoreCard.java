package dat255.chalmers.stormystreet.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import dat255.chalmers.stormystreet.R;

/**
 * This is a class for displaying highscore cards in the highscore view
 * @author Kevin Hoogendijk
 * @since 2015-10-14
 */
public class HighscoreCard extends CardView {
    private TextView placeView, nameView, scoreView;
    private ProfilePictureView pictureView;

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
        pictureView = (ProfilePictureView) findViewById(R.id.highscorecard_picture);
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
            this.scoreView.setText(Integer.toString(score));
            this.scoreView.setVisibility(View.VISIBLE);
    }

    public void setFacebookId(String id){
        if(id != null){
            this.pictureView.setProfileId(id);
            this.pictureView.setVisibility(View.VISIBLE);
        }
    }

}
