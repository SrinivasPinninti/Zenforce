package com.evoke.zenforce.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.database.dao.VisitDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseFragment extends Fragment {


    private static final String TAG = "BaseFragment";
    private Context mContext;
    private Activity mActivity;



    protected abstract Fragment getFragment();
    protected abstract List<VisitBean> getData();

    public BaseFragment() {
        // Required empty public constructor
    }

    /*// TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myplaces, container, false);
        getLoaderManager().initLoader(111, null, mVisitLoader);
        return v;
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

    private ArrayList<VisitBean> mData;
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

                mData = new ArrayList<VisitBean>();

                do {

                    VisitDAO dao = VisitDAO.getSingletonInstance(mContext);
                    VisitBean bean = (VisitBean) dao.populate(cursor);
                    mData.add(bean);

                } while (cursor.moveToNext());


                Log.v(TAG, " no of places from db :  " + mData.size());

                Comparator<VisitBean> comparator = new Comparator<VisitBean>() {
                    @Override
                    public int compare(VisitBean lhs, VisitBean rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                };

                Collections.sort(mData, comparator);


            }


        }

        @Override
        public void onLoaderReset(Loader loader) {
//            mRecyclerView.swapAdapter(null, true);
        }
    };

}