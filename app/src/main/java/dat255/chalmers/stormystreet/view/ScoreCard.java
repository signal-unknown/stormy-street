package dat255.chalmers.stormystreet.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.IScore;

/**
 * @author Alexander Håkansson
 * @since 2015-09-23
 */
public class ScoreCard extends CardView {

    private TextView textScore;
    private static final String INITIAL_SCORE = "0";

    public ScoreCard(Context context) {
        super(context);
        init();
    }

    public ScoreCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScoreCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.cardview_scorecard, this);

        textScore = (TextView) findViewById(R.id.scorecard_score);
        textScore.setText(INITIAL_SCORE);
    }

    public void setScore(IScore score) {
        textScore.setText(score.toString());
    }
}
