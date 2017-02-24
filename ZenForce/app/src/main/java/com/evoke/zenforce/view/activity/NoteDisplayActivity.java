package com.evoke.zenforce.view.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.NoteEntityBean;
import com.evoke.zenforce.model.database.dao.NoteDAO;
import com.evoke.zenforce.view.application.ZenForceApplication;

import java.util.ArrayList;
import java.util.List;

public class NoteDisplayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String TAG = "NoteDisplayActivity";

    //    private LinearLayout thumbnailsContainer;
    private ImageView btnNext;
    private ImageView btnPrev;
    private NotesListAdapter adapter;

    private long mVisitId;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);
        Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (bundle != null) {

            mVisitId   = bundle.getLong("VISIT_ID", 0);
        }

        Log.d(TAG, " mVisitId : " + mVisitId);
        getSupportLoaderManager().initLoader(0, null, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ZenForceApplication.getInstance());
        mRecyclerView.setLayoutManager(layoutManager);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        NoteDAO dao = NoteDAO.getSingletonInstance(NoteDisplayActivity.this);
        Log.d(TAG, "mLoader mVisitId : " + mVisitId);
        String selection = DbConstants.NoteTable.COLUMN_VISIT_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(mVisitId)};

        return new CursorLoader(NoteDisplayActivity.this, dao.getURI(), null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished.....");
        if (cursor != null && cursor.moveToFirst()) {
            NoteDAO dao = NoteDAO.getSingletonInstance(NoteDisplayActivity.this);
            List<NoteEntityBean> noteEntityBeanList = new ArrayList<>();

            do {

                NoteEntityBean photoBean = (NoteEntityBean) dao.populate(cursor);
                noteEntityBeanList.add(photoBean);

            } while (cursor.moveToNext());

            Log.d(TAG, "noteEntityBeanList  : " + noteEntityBeanList.size());
            adapter = new NotesListAdapter(this, noteEntityBeanList);
            mRecyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }




   /* public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<NoteEntityBean> noteList;

        public ViewPagerAdapter(FragmentManager fm, List<NoteEntityBean> noteList) {
            super(fm);
            this.noteList = noteList;
        }

        @Override
        public Fragment getItem(int position) {
            NoteEntityBean note = noteList.get(position);
            return NoteFragment.getInstance(note.getNote());
        }

        @Override
        public int getCount() {
            return noteList.size();
        }
    }*/

    public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.PlaceViewHolder> {


        private List<NoteEntityBean> notesList;
        private Context context;


        public NotesListAdapter(Context context, List<NoteEntityBean> notesList) {
            this.context = context;
            this.notesList = notesList;
        }

        @Override
        public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_notes, parent, false);
            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PlaceViewHolder holder, int position) {
            holder.bindView(notesList.get(position));
        }

        @Override
        public int getItemCount() {
            return notesList.size();
        }

        public class PlaceViewHolder extends RecyclerView.ViewHolder {

            private TextView notes;

            public PlaceViewHolder(View itemView) {
                super(itemView);
                notes = (TextView) itemView.findViewById(R.id.notes);

            }
            public void bindView(NoteEntityBean note) {
                notes.setText(note.getNote());
            }

        }

    }
}
