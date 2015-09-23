package dat255.chalmers.stormystreet.controller;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.IScore;
import dat255.chalmers.stormystreet.view.ScoreCard;

/**
 * @author Alexander Håkansson
 * @since 2015-09-22
 */
public class HomeActivityFragment extends Fragment {

    private ScoreCard scoreCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scoreCard = (ScoreCard) view.findViewById(R.id.home_scoreCard);
    }
}
