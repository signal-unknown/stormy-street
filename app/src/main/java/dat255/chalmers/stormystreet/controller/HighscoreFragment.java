package dat255.chalmers.stormystreet.controller;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
public class HighscoreFragment extends Fragment implements IModelListener, FacebookCallback<LoginResult> {
    private MainModel model;

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private LinearLayoutManager recycleViewManager;
    private LoginButton facebookLoginButton;

    private CallbackManager facebookCallbackManager;

    private static final String FACEBOOK_USER_FRIENDS = "user_friends";
    private static final String FACEBOOK_USER_GAMES = "user_games_activity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_highscore, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        facebookLoginButton = (LoginButton) view.findViewById(R.id.highscore_facebook_login_button);
        model = ((GlobalState)getActivity().getApplication()).getModel();
        model.addListener(this);
        FacebookAPIProxy.getScores(model);
        setupRecyclerView(view);
        setupFacebook();
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
            stats.add(new HighscoreCardData(i+1, friend.getName(), friend.getId(), friend.getMetersTraveled()));
        }
        recyclerViewAdapter = new HighscoreListAdapter(stats);
        cardRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setupFacebook() {
        int buttonPadding = getResources().getDimensionPixelSize(R.dimen.facebook_button_padding);
        facebookLoginButton.setPadding(buttonPadding, buttonPadding, buttonPadding, buttonPadding);

        facebookCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton.setFragment(this);
        facebookLoginButton.registerCallback(facebookCallbackManager, this);
        facebookLoginButton.setReadPermissions(FACEBOOK_USER_FRIENDS, FACEBOOK_USER_GAMES);

        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        if (facebookToken != null && !facebookToken.isExpired()) {
            facebookLoginButton.setVisibility(View.GONE);
            cardRecyclerView.setVisibility(View.VISIBLE);
        } else {
            facebookLoginButton.setVisibility(View.VISIBLE);
            cardRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void modelUpdated() {
        updateCards();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        facebookLoginButton.setVisibility(View.GONE);
        cardRecyclerView.setVisibility(View.VISIBLE);
        FacebookAPIProxy.getScores(((GlobalState)getActivity().getApplication()).getModel());
        updateCards();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

    }

    class ScoreComparator implements Comparator<FacebookFriend>{
        @Override
        public int compare(FacebookFriend lhs, FacebookFriend rhs) {
            return rhs.getMetersTraveled() - lhs.getMetersTraveled();
        }
    }
}
