package dat255.chalmers.stormystreet.controller;

import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.FacebookFriend;
import dat255.chalmers.stormystreet.model.IModelListener;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.utilities.FacebookAPIProxy;
import dat255.chalmers.stormystreet.view.HighscoreCardData;

/**
 * @author Kevin Hoogendijk  and David Fogelberg
 * @since 2015-10-13
 */
public class HighscoreFragment extends Fragment implements IModelListener {
    private MainModel model;

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private LinearLayoutManager recycleViewManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_highscore, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ((GlobalState)getActivity().getApplication()).getModel();
        model.addListener(this);
        FacebookAPIProxy.getScores(model);
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        model = ((GlobalState) getActivity().getApplication()).getModel();
        cardRecyclerView = (RecyclerView) view.findViewById(R.id.higscore_recyclerview);

        recycleViewManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cardRecyclerView.setLayoutManager(recycleViewManager);
    }

    public synchronized void updateCards(){
        List<HighscoreCardData> stats = new ArrayList<>();
        List<FacebookFriend> friends = model.getHighscoreList();
        Collections.sort(friends, new ScoreComparator());
        for(int i = 0; i < friends.size(); i++){
            FacebookFriend friend = friends.get(i);
            stats.add(new HighscoreCardData(i+1, friend.getName(), friend.getMetersTraveled()));
        }
        recyclerViewAdapter = new HighscoreListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void modelUpdated() {
        updateCards();
    }

    class ScoreComparator implements Comparator<FacebookFriend>{
        @Override
        public int compare(FacebookFriend lhs, FacebookFriend rhs) {
            return rhs.getMetersTraveled() - lhs.getMetersTraveled();
        }
    }
}
