package com.fastandinstantly.instantaneous;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etsy.android.grid.ExtendableListView;
import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SearchFragment extends Fragment {
    private GridView mGridView;
    private boolean mHasRequestedMore;
    private AdapterUsers mAdapter;
    private List<User> mData;
    GridView mListView ;
    final App app = (App) getApplicationContext();
    int counter = 0;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();

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
        view = inflater.inflate(R.layout.fragment_search, container, false);


        mData = app.getUsers();

        mListView = (GridView) view.findViewById(android.R.id.list);
        mListView.setLayoutParams( new LinearLayout.LayoutParams(
                ExtendableListView.LayoutParams.MATCH_PARENT,
                ExtendableListView.LayoutParams.MATCH_PARENT, 1.0f));
        final EditText searchValue = (EditText)view.findViewById(R.id.edtSearch);
        final View finalView = view;
        searchValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<User> temp = null;
                EditText editSearch = (EditText) finalView.findViewById(R.id.edtSearch);
                int textlength = searchValue.getText().length();
                mListView.invalidateViews();
                mAdapter.clear();

                counter = 0;
               if(temp != null) {
                    temp.clear();
                }
                mAdapter.notifyDataSetChanged();

                for (int x = 0; x < mData.size(); x++){
                    if(mData.get(x).getId().equals(editSearch.getText().toString())) {
                        mAdapter.add(mData.get(x));
                    }else if (mData.get(x).getName().toUpperCase().contains(editSearch.getText().toString().toUpperCase())) {
                        mAdapter.add(mData.get(x));
                    }
                }


                mListView.setAdapter(mAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

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
