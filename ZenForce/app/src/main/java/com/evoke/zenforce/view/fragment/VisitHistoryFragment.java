package com.evoke.zenforce.view.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.adapter.TodayListAdapter;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.database.dao.VisitDAO;
import com.evoke.zenforce.view.activity.VisitActivity;
import com.evoke.zenforce.view.application.ZenForceApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VisitHistoryFragment extends Fragment {

    private static final String TAG = "History";
    private Context mContext;


    private long mVisitId;


    private RecyclerView mRecyclerView;
    private TodayListAdapter mAdapter;


    public VisitHistoryFragment() {
        // Required empty public constructor
    }

    public static VisitHistoryFragment newInstance() {
        VisitHistoryFragment fragment = new VisitHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated....");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VisitActivity activity = (VisitActivity) getActivity();
        mVisitId = activity.getPlaceId();
//        mLocationId = activity.getPlaceId();
        Log.v(TAG, "onCreateView  mVisitId : " + mVisitId);
        View v = inflater.inflate(R.layout.fragment_visit_history, container, false);
        initUI(v);

        getLoaderManager().initLoader(7, null, mLoader);
        return v;
    }


    private void initUI(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ZenForceApplication.getInstance());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
//        Log.v(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        Log.v(TAG, "onDetach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    private ArrayList<VisitBean> visitList;
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

                visitList = new ArrayList<VisitBean>();

                do {

                    VisitDAO dao = VisitDAO.getSingletonInstance(mContext);
                    VisitBean bean = (VisitBean) dao.populate(cursor);
                    visitList.add(bean);

                } while (cursor.moveToNext());


                Log.v(TAG, " visitList list  :  " + visitList.size());

                Comparator<VisitBean> comparator = new Comparator<VisitBean>() {
                    @Override
                    public int compare(VisitBean lhs, VisitBean rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                };

                Collections.sort(visitList, comparator);

                mAdapter = new TodayListAdapter(mContext, visitList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }


        }

        @Override
        public void onLoaderReset(Loader loader) {
            mRecyclerView.swapAdapter(null, true);
        }
    };



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
