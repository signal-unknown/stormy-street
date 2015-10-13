package dat255.chalmers.stormystreet.controller;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import dat255.chalmers.stormystreet.view.StatCardData;

/**
 * @author Alexander Håkansson
 * @since 2015-09-22
 */
public class HomeFragment extends Fragment implements IModelListener{

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private GridLayoutManager recycleViewManager;

    private MainModel model;

    private Bitmap personIcon;
    private Bitmap celsiusIcon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_person_black_24dp);
        celsiusIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_temperature_celsius_black_24dp);

        ((GlobalState)getActivity().getApplication()).getModel().addListener(this);
        setupRecyclerView(view);
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
                if (position == 0) {
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

        if (Looper.myLooper() == Looper.getMainLooper()) {
            updateCards();
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCards();
                    }
                });
            }
        }

    }

    public synchronized void updateCards(){
        List<StatCardData> stats = new ArrayList<>();
        stats.add(new StatCardData(model.getTotalPlusCurrentScore().toString(), getString(R.string.points), null));

        /*if (model.getIsOnBus()) {
            try {
                IBus currentBus = model.getBus(model.getCurrentTrip().getCurrentVinNumber());
                // Show next stop
                stats.add(new StatCardData(currentBus.getNextStop(), getString(R.string.next_stop), null));
                // Show current bus reg number
                stats.add(new StatCardData(Constants.vinToRegNr(currentBus.getDgwNumber()), getString(R.string.current_bus), null));
                // Show bus speed
                stats.add(new StatCardData(Double.toString(currentBus.getGPSPosition().getSpeed()), getString(R.string.kmh), null));
                // Show degrees in bus
                stats.add(new StatCardData(Integer.toString(currentBus.getDriverCabinTemperature()), null, celsiusIcon));
                // Show how many are using wifi
                stats.add(new StatCardData(Integer.toString(currentBus.getOnlineUsers()), getString(R.string.on_wifi), personIcon));
            } catch (BusNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // TODO: Show other cards when not on bus
        }*/

        recyclerViewAdapter = new BusStatListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }
}
