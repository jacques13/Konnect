package com.fastandinstantly.instantaneous;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etsy.android.grid.ExtendableListView;
import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.facebook.FacebookSdk.getApplicationContext;


public class RandomFragment extends Fragment {

    private StaggeredGridView mGridView;
    private boolean mHasRequestedMore;
    private AdapterUsers mAdapter;
    private List<User> mData;
   GridView mListView ;
    final App app = (App) getApplicationContext();

    public RandomFragment() {
        // Required empty public constructor
    }


    public static RandomFragment newInstance() {
        RandomFragment fragment = new RandomFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_random, container, false);

        app.randomUsers();
        mData = app.getRandoms();

        mListView = (GridView) view.findViewById(android.R.id.list);
        FancyButton btnRandom = (FancyButton)view.findViewById(R.id.random) ;
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.invalidateViews();
                mAdapter.clear();
                mData.clear();
                app.randomUsers();
                mData = app.getRandoms();
                mAdapter.notifyDataSetChanged();
                for (User data : mData) {
                    mAdapter.add(data);
                    }

                mListView.setAdapter(mAdapter);


            }
        });
        if (savedInstanceState == null) {
            final LayoutInflater layoutInflater = getActivity().getLayoutInflater();


        }
        if (mAdapter == null) {
            mAdapter = new AdapterUsers(getActivity(), R.id.txt_line1);
        }


        for (User data : mData) {
            mAdapter.add(data);
            Log.i("some",data.getName());
        }
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount){
                if (!mHasRequestedMore) {
                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if (lastInScreen >= totalItemCount) {
                        Log.d("test", "onScroll lastInScreen - so load more");
                        mHasRequestedMore = true;
                        onLoadMoreItems();
                    }
                }
            }

        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ((IntroActivity)getActivity()).setUserID(mData.get(position).getId());
                ((IntroActivity)getActivity()).gotoProfile();

            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

    private void onLoadMoreItems() {
        /*for (User data :   mData) {
            if(mData.size() == app.getUsers().size()) {
                mAdapter.add(data);
            }
        }
        // stash all the data in our backing store
        mData.addAll( mData);
        // notify the adapter that we can update now
        mAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;*/
    }



}
