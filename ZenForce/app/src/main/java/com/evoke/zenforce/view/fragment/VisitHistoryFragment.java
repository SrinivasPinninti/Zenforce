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
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.adapter.VisitedHistoryListAdapter;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.NoteEntityBean;
import com.evoke.zenforce.model.database.beanentity.PhotoEntityBean;
import com.evoke.zenforce.model.database.beanentity.TimerEntityBean;
import com.evoke.zenforce.model.database.dao.NoteDAO;
import com.evoke.zenforce.model.database.dao.PhotoDAO;
import com.evoke.zenforce.model.database.dao.TimerDAO;
import com.evoke.zenforce.view.activity.VisitActivity;
import com.evoke.zenforce.view.application.ZenForceApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitHistoryFragment extends Fragment {

    private static final String TAG = "History";
    private Context mContext;

    private TextView tvDate;
    private TextView tvImageNumber;
    private TextView tvNotesNumber;
    private TextView tvVisitNumber;

    private long mVisitId;
//    private String mLocationId;


    private RecyclerView mRecyclerView;
    private VisitedHistoryListAdapter mVisitHistoryAdapter;

    private Map<String, Integer> map = new HashMap<String, Integer>();



    private boolean photo, note, time;

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
        mVisitId = activity.getVisitId();
//        mLocationId = activity.getLocationId();
        Log.v(TAG, "onCreateView  mVisitId : " + mVisitId);
        View v = inflater.inflate(R.layout.fragment_visit_history, container, false);
        initUI(v);

        getLoaderManager().initLoader(7, null, mLoaderCallbacks);
        getLoaderManager().initLoader(8, null, mLoaderCallbacks);
        getLoaderManager().initLoader(9, null, mLoaderCallbacks);

        return v;
    }


    private void initUI(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ZenForceApplication.getInstance());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        tvDate        = (TextView) v.findViewById(R.id.tvDate);
        tvImageNumber = (TextView) v.findViewById(R.id.tvImageNumber);
        tvNotesNumber = (TextView) v.findViewById(R.id.tvNotesNumber);
        tvVisitNumber = (TextView) v.findViewById(R.id.tvVisitNumber);
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

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, " onCreateLoader....mVisitId :  " + mVisitId);
            switch (id) {

                case 7: {

                    PhotoDAO dao = PhotoDAO.getSingletonInstance(mContext);
                    String selection = DbConstants.PhotoTable.COLUMN_VISIT_ID + " = ?";
                    String[] selectionArgs = new String[]{String.valueOf(mVisitId)};

//                  String sortOrder = DbConstants.VisitTable.COLUMN_TIMESTAMP + " DESC LIMIT 7 ";
                    return new CursorLoader(mContext, dao.getURI(), null, selection, selectionArgs, null);
                }
                case 8:
                {

                    NoteDAO dao = NoteDAO.getSingletonInstance(mContext);
                    String selection = DbConstants.NoteTable.COLUMN_VISIT_ID + " = ?";
                    String[] selectionArgs = new String[]{String.valueOf(mVisitId)};

//                  String sortOrder = DbConstants.VisitTable.COLUMN_TIMESTAMP + " DESC LIMIT 7 ";
                    return new CursorLoader(mContext, dao.getURI(), null, selection, selectionArgs, null);

                }

                case 9:
                {
                    TimerDAO dao = TimerDAO.getSingletonInstance(mContext);
                    String selection = DbConstants.TimerTable.COLUMN_VISIT_ID + " = ?";
                    String[] selectionArgs = new String[]{String.valueOf(mVisitId)};

//                  String sortOrder = DbConstants.VisitTable.COLUMN_TIMESTAMP + " DESC LIMIT 7 ";
                    return new CursorLoader(mContext, dao.getURI(), null, selection, selectionArgs, null);
                }


            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

            Log.v(TAG, " onLoadFinished  id :  " +loader.getId());


            switch (loader.getId()) {

                case 7:
                if (cursor != null && cursor.moveToFirst()) {

                    ArrayList<PhotoEntityBean> photos = new ArrayList<PhotoEntityBean>();

                    do {
                        PhotoDAO dao = PhotoDAO.getSingletonInstance(mContext);
                        PhotoEntityBean photoEntityBean = (PhotoEntityBean) dao.populate(cursor);
                        photos.add(photoEntityBean);
                    } while (cursor.moveToNext());

                    Log.v(TAG, "photos " + photos.size());
                    map.put("PHOTO",photos.size());
                }


                    photo = true;

                    break;

                case 8:

                    if (cursor != null && cursor.moveToFirst()) {

                        ArrayList<NoteEntityBean> notes = new ArrayList<NoteEntityBean>();

                        do {
                            NoteDAO dao = NoteDAO.getSingletonInstance(mContext);
                            NoteEntityBean noteEntityBean = (NoteEntityBean) dao.populate(cursor);
                            notes.add(noteEntityBean);
                        } while (cursor.moveToNext());

                        Log.v(TAG, "notes " + notes.size());
                        map.put("NOTE", notes.size());
                    }

                    note = true;
                    break;

                case 9:

                    if (cursor != null && cursor.moveToFirst()) {

                        ArrayList<TimerEntityBean> times = new ArrayList<TimerEntityBean>();

                        do {
                            TimerDAO dao = TimerDAO.getSingletonInstance(mContext);
                            TimerEntityBean timerEntityBean = (TimerEntityBean) dao.populate(cursor);
                            times.add(timerEntityBean);
                        } while (cursor.moveToNext());

                        Log.v(TAG, "times " + times.size());
                        map.put("TIMER", times.size());
                    }

                    time = true;
                    break;
            }

            if (photo && note && time) {

                List<Map<String, Integer>> list = new ArrayList<>();

                photo= false;
                time= false;
                note = false;

                Log.v(TAG, " set map to adapter : " + map.size());
                list.add(map);

                Log.v(TAG, " list : " + list.size());
                mVisitHistoryAdapter = new VisitedHistoryListAdapter(mContext, list);
                mRecyclerView.setAdapter(mVisitHistoryAdapter);
            }





        }



        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

//            mVisitHistoryAdapter.setData(null, false, false);
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
