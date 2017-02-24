package com.evoke.zenforce.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.adapter.VisitedPlacesListAdapter;
import com.evoke.zenforce.model.database.beanentity.PlaceBean;
import com.evoke.zenforce.model.database.dao.PlaceDAO;
import com.evoke.zenforce.model.database.dao.VisitDAO;
import com.evoke.zenforce.view.activity.PlaceMapActivity;
import com.evoke.zenforce.view.application.ZenForceApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VisitedPlacesFragment extends Fragment {

    private static final String TAG = "VisitedPlacesFragment";


    private Context mContext;

    private EditText etSearch;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private VisitedPlacesListAdapter mNewPlacesListAdapter;
    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private Activity mActivity;

    private  ArrayList<PlaceBean> mPlaceList = null;
    private RelativeLayout RlNoPlaces;


    public VisitedPlacesFragment() {
        // Required empty public constructor
    }

    public static VisitedPlacesFragment newInstance() {
        VisitedPlacesFragment fragment = new VisitedPlacesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myplaces, container, false);
        Log.v(TAG, "onCreateView");
        initUI(v);
        getLoaderManager().initLoader(111, null, mVisitLoader);
        return v;
    }


    private void initUI(View v) {
        etSearch = (EditText) v.findViewById(R.id.etSearch);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ZenForceApplication.getInstance());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        fab           = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myPlacesIntent = new Intent(mContext, PlaceMapActivity.class);
//                Intent myPlacesIntent = new Intent(mContext, TestActivity.class);
                myPlacesIntent.putExtra("NAV_MY_PLACES", mContext.getString(R.string.new_place));
                myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(myPlacesIntent);
            }
        });
//        addTextChangeListener();
        RlNoPlaces = (RelativeLayout) v.findViewById(R.id.RlNoPlaces);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
        Log.v(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");

    }

    LoaderManager.LoaderCallbacks<Cursor> mVisitLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            Log.v(TAG, "onCreateLoader id : " + id);
            VisitDAO dao = VisitDAO.getSingletonInstance(mContext);
            return new CursorLoader(mContext, dao.getURI(), null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor cursor) {
            Log.v(TAG, "onLoadFinished.... "+ loader.getId());


            if (cursor != null && cursor.moveToFirst()) {


                RlNoPlaces.setVisibility(View.GONE);
                etSearch.setVisibility(View.VISIBLE);
                addTextChangeListener();

                mPlaceList = new ArrayList<PlaceBean>();

                Log.v(TAG, " cursor count " + cursor.getCount());
                do {

                    PlaceDAO dao = PlaceDAO.getSingletonInstance(mContext);
                    PlaceBean bean = (PlaceBean) dao.populate(cursor);
                    mPlaceList.add(bean);

                } while (cursor.moveToNext());


                Log.v(TAG, " no of places from db :  " + mPlaceList.size());

                Comparator<PlaceBean> comparator = new Comparator<PlaceBean>() {
                    @Override
                    public int compare(PlaceBean lhs, PlaceBean rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                };

                Collections.sort(mPlaceList, comparator);

                mNewPlacesListAdapter = new VisitedPlacesListAdapter(mContext, mPlaceList);
                mRecyclerView.setAdapter(mNewPlacesListAdapter);
                mNewPlacesListAdapter.notifyDataSetChanged();

            } else {
                RlNoPlaces.setVisibility(View.VISIBLE);
            }



        }

        @Override
        public void onLoaderReset(Loader loader) {
            mRecyclerView.swapAdapter(null, true);
        }
    };



    private void addTextChangeListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String query = s.toString().toLowerCase();

                if (mPlaceList != null) {

                    ArrayList<PlaceBean> searchList = new ArrayList<PlaceBean>();

                    int size = mPlaceList.size();

                    for (int i=0; i<size; i++) {
                        PlaceBean place = mPlaceList.get(i);
                        String name = place.getName().toLowerCase();

                        if (name.contains(query)) {
                            searchList.add(place);
                        }

                    }

                    mNewPlacesListAdapter = new VisitedPlacesListAdapter(mContext, searchList);
                    mRecyclerView.setAdapter(mNewPlacesListAdapter);
                    mNewPlacesListAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if (!getActivity().isFinishing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left+30, top, right+30, bottom);
                mDivider.draw(c);
            }
        }
    }
}
