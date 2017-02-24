package com.evoke.zenforce.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.adapter.TodayListAdapter;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.database.dao.VisitDAO;
import com.evoke.zenforce.view.application.ZenForceApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TodayFragment extends Fragment {

    private static final String TAG = "TodayFragment";


    private Context mContext;

    private EditText etSearch;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private TodayListAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private Activity mActivity;

    private ArrayList<VisitBean> mTodayList = null;
    private RelativeLayout RlNoVisits;


    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
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

        View v = inflater.inflate(R.layout.fragment_today, container, false);
        Log.v(TAG, "onCreateView");
        initUI(v);
        getLoaderManager().initLoader(333, null, mLoader);
        return v;
    }


    private void initUI(View v) {

        etSearch = (EditText) v.findViewById(R.id.etSearch);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.today_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ZenForceApplication.getInstance());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));

//        addTextChangeListener();
        RlNoVisits = (RelativeLayout) v.findViewById(R.id.RlNoVisits);
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

    LoaderManager.LoaderCallbacks<Cursor> mLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
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
                RlNoVisits.setVisibility(View.GONE);
//                etSearch.setVisibility(View.VISIBLE);
//                addTextChangeListener();

                mTodayList = new ArrayList<VisitBean>();

                do {

                    VisitDAO dao = VisitDAO.getSingletonInstance(mContext);
                    VisitBean bean = (VisitBean) dao.populate(cursor);
//                    mTodayList.add(bean);
                    Log.v(TAG, " time stamp  :  " + bean.getTimeStamp());
                    if (DateUtils.isToday(bean.getTimeStamp())) {
                        mTodayList.add(bean);

                    }

                } while (cursor.moveToNext());


                Log.v(TAG, " today list  :  " + mTodayList.size());

                Comparator<VisitBean> comparator = new Comparator<VisitBean>() {
                    @Override
                    public int compare(VisitBean lhs, VisitBean rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                };

                Collections.sort(mTodayList, comparator);

                mAdapter = new TodayListAdapter(mContext, mTodayList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            } else {
                RlNoVisits.setVisibility(View.VISIBLE);
            }



        }

        @Override
        public void onLoaderReset(Loader loader) {
            mRecyclerView.swapAdapter(null, true);
        }
    };



   /* private void addTextChangeListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String query = s.toString().toLowerCase();

                if (mTodayList != null) {

                    ArrayList<VisitBean> searchList = new ArrayList<VisitBean>();

                    int size = mTodayList.size();

                    for (int i=0; i<size; i++) {
                        VisitBean visit = mTodayList.get(i);
                        String name = visit.getName().toLowerCase();

                        if (name.contains(query)) {
                            searchList.add(visit);
                        }

                    }

                    mAdapter = new VisitedPlacesListAdapter(mContext, searchList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }*/


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

    public void onImageSelcted() {

    }
}
