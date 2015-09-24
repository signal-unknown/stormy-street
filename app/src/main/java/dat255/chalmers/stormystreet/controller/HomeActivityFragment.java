package dat255.chalmers.stormystreet.controller;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.IScore;
import dat255.chalmers.stormystreet.view.BusStat;
import dat255.chalmers.stormystreet.view.ScoreCard;

/**
 * @author Alexander Håkansson
 * @since 2015-09-22
 */
public class HomeActivityFragment extends Fragment {

    private ScoreCard scoreCard;

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private GridLayoutManager recycleViewManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        cardRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);

        // use a linear layout manager
        recycleViewManager = new GridLayoutManager(getActivity(), 2); // 2 columns
        recycleViewManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });

        cardRecyclerView.setLayoutManager(recycleViewManager);

        List<BusStat> stats = new ArrayList<>();
        stats.add(new BusStat("6", "poäng", null));
        stats.add(new BusStat("X", "stopp", null));
        stats.add(new BusStat("6", "poäng", null));
        stats.add(new BusStat("X", "stopp", null));

        recyclerViewAdapter = new BusStatListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }
}
