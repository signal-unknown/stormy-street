package dat255.chalmers.stormystreet.controller;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockApplication;
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

    private Toolbar toolbar;

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

        List<StatCardData> stats = new ArrayList<>();
        Bitmap personIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_person_black_24dp);
        stats.add(new StatCardData(model.getCurrentUser().getStatistics().getTotalScore().toString(), model.getCurrentUser().getStatistics().getTotalScore().getSuffix() , null));
        stats.add(new StatCardData("O", "stopp", null));
        stats.add(new StatCardData("3", "personer", personIcon));
        stats.add(new StatCardData("34", "km/h", null));
        stats.add(new StatCardData(Long.toString(model.getCurrentUser().getStatistics().getTimeSpentOnBus()/10000), "timmar", null));

        recyclerViewAdapter = new BusStatListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }

    public void modelUpdated() {
        //TODO: Update view with info from model
    }
}
