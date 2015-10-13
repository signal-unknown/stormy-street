package dat255.chalmers.stormystreet.controller;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.view.StatCardData;

/**
 * @author Kevin Hoogendijk  and David Fogelberg
 * @since 2015-10-13
 */
public class HighscoreFragment extends Fragment {
    private MainModel model;

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private GridLayoutManager recycleViewManager;

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

        // use a linear layout manager
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            recycleViewManager = new GridLayoutManager(getActivity(), 2); // 2 columns if vertical
        } else {
            recycleViewManager = new GridLayoutManager(getActivity(), 3); // 3 columns if horizontal
        }

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
        updateCards();
    }

    public synchronized void updateCards(){
        List<StatCardData> stats = new ArrayList<>();
        stats.add(new StatCardData("1337 looool", null, null));

        recyclerViewAdapter = new BusStatListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }
}
