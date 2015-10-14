package dat255.chalmers.stormystreet.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.view.HighscoreCardData;

/**
 * @author Kevin Hoogendijk  and David Fogelberg
 * @since 2015-10-13
 */
public class HighscoreFragment extends Fragment {
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

        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        model = ((GlobalState) getActivity().getApplication()).getModel();
        cardRecyclerView = (RecyclerView) view.findViewById(R.id.higscore_recyclerview);

        recycleViewManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cardRecyclerView.setLayoutManager(recycleViewManager);
        updateCards();
    }

    public synchronized void updateCards(){
        List<HighscoreCardData> stats = new ArrayList<>();
        stats.add(new HighscoreCardData(1, "Kevin", 1000));
        stats.add(new HighscoreCardData(2, "Maxim", 730));
        stats.add(new HighscoreCardData(3, "Alexander", 700));
        stats.add(new HighscoreCardData(3, "Alexander2", 400));
        stats.add(new HighscoreCardData(3, "David", 200));
        recyclerViewAdapter = new HighscoreListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }
}
