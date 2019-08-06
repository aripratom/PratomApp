package com.aripratom.pratomapp.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aripratom.pratomapp.R;
import com.aripratom.pratomapp.activity.FriendsAddActivity;
import com.aripratom.pratomapp.adapter.FriendsAdapter;
import com.aripratom.pratomapp.click.LoadFriendsCallback;
import com.aripratom.pratomapp.db.FriendsHelper;
import com.aripratom.pratomapp.entity.Friends;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.aripratom.pratomapp.activity.FriendsAddActivity.REQUEST_UPDATE;


/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */

public class FriendsFragment extends Fragment implements View.OnClickListener, LoadFriendsCallback {
    private RecyclerView rvFriends;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;
    public static final String EXTRA_STATE = "EXTRA_STATE";
    private FriendsAdapter adapter;
    private FriendsHelper helper;



    public FriendsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new FriendsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        rvFriends = view.findViewById(R.id.rv_category);
        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFriends.setHasFixedSize(true);

        helper = FriendsHelper.getInstance(getContext());
        helper.open();
        progressBar = view.findViewById(R.id.progressbar);
        fabAdd = view.findViewById(R.id.fab);
        fabAdd.setOnClickListener(this);

        adapter = new FriendsAdapter(getActivity());
        rvFriends.setAdapter(adapter);

        if (savedInstanceState == null){
            new LoadFriendsAsync(helper,this).execute();
        }else {
            ArrayList<Friends> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapter.setListFriends(list);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull  Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListFriends());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab){
            Intent intent = new Intent(getActivity(), FriendsAddActivity.class);
            startActivityForResult(intent, FriendsAddActivity.REQUEST_ADD);
        }
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
               progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Friends> friends) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListFriends(friends);
    }

    private static class LoadFriendsAsync extends AsyncTask<Void, Void, ArrayList<Friends>> {
        private final WeakReference<FriendsHelper> weakFriendsHelper;
        private final WeakReference<LoadFriendsCallback> weakCallback;

        private LoadFriendsAsync(FriendsHelper friendsHelper, LoadFriendsCallback callback){
            weakFriendsHelper = new WeakReference<>(friendsHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Friends> doInBackground(Void... voids) {
            return weakFriendsHelper.get().getAllFriends();
        }

        @Override
        protected void onPostExecute(ArrayList<Friends> friends) {
            super.onPostExecute(friends);
            weakCallback.get().postExecute(friends);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == FriendsAddActivity.REQUEST_ADD){
                if(resultCode == FriendsAddActivity.RESULT_ADD){
                    Friends friends = data.getParcelableExtra(FriendsAddActivity.EXTRA_FRIENDS);
                    adapter.addItem(friends);
                    rvFriends.smoothScrollToPosition(adapter.getItemCount() - 1);
                    showSnackbarMessage("Satu item berhasil ditambahkan");
                }
            }
            else if (requestCode == REQUEST_UPDATE){
                if (resultCode == FriendsAddActivity.RESULT_UPDATE){
                    Friends friends = data.getParcelableExtra(FriendsAddActivity.EXTRA_FRIENDS);
                    int position = data.getIntExtra(FriendsAddActivity.EXTRA_POSITION, 0);
                    adapter.updateItem(position, friends);
                    rvFriends.smoothScrollToPosition(position);
                    showSnackbarMessage("Satu item berhasil diubah");
                }

                else if (resultCode == FriendsAddActivity.RESULT_DELETE){
                    int position = data.getIntExtra(FriendsAddActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                    showSnackbarMessage("Satu item berhasil dihapus");
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFriendsAsync(helper, this).execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFriends, message, Snackbar.LENGTH_SHORT).show();
    }

}
