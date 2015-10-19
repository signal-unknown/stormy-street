package dat255.chalmers.stormystreet.controller;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
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
import dat255.chalmers.stormystreet.model.IModelListener;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.services.BusInfoUpdater;
import dat255.chalmers.stormystreet.utilities.BusStatsUtil;
import dat255.chalmers.stormystreet.view.StatCardData;

/**
 * @author Alexander Håkansson
 * @since 2015-09-22
 */
public class HomeFragment extends Fragment implements IModelListener, BusInfoUpdater.IBusInfoListener{

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private GridLayoutManager recycleViewManager;

    private MainModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((GlobalState)getActivity().getApplication()).getModel().addListener(this);
        setupRecyclerView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        model = ((GlobalState)getActivity().getApplication()).getModel();
        if (model.getIsOnBus()) {
            new BusInfoUpdater(this).execute(model.getCurrentTrip().getCurrentVinNumber());
        }
    }

    private void setupRecyclerView(View view) {
        model = ((GlobalState) getActivity().getApplication()).getModel();
        cardRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);

        // use a linear layout manager
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            recycleViewManager = new GridLayoutManager(getActivity(), 2); // 2 columns if vertical
        } else {
            recycleViewManager = new GridLayoutManager(getActivity(), 3); // 3 columns if horizontal
        }

        recycleViewManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });

        cardRecyclerView.setLayoutManager(recycleViewManager);

        modelUpdated();
    }

    @Override
    public void modelUpdated() {
        model = ((GlobalState) getActivity().getApplication()).getModel();

        if (model.getIsOnBus()) {
            // Starting thread for getting data from model
            new BusInfoUpdater(this).execute(model.getCurrentTrip().getCurrentVinNumber());
        } else {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                updatePoints();
            } else {
                Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updatePoints();
                        }
                    });
                }
            }
        }
    }

    public synchronized void updatePoints() {
        List<StatCardData> stats = new ArrayList<>();

        stats.add(new StatCardData(model.getTotalPlusCurrentScore().toString(), getString(R.string.points), null));

        recyclerViewAdapter = new BusStatListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }


    public synchronized void updateCards(final IBus bus){
        if (bus != null) {
            model.addBus(bus);

            List<StatCardData> stats = new ArrayList<>();

            stats.add(new StatCardData(model.getTotalPlusCurrentScore().toString(), getString(R.string.points), null));;

            stats.addAll(BusStatsUtil.getBusStatCards(getActivity(), bus));

            recyclerViewAdapter = new BusStatListAdapter(stats);
            cardRecyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public void busUpdated(final IBus bus) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            updateCards(bus);
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCards(bus);
                    }
                });
            }
        }
    }
}
