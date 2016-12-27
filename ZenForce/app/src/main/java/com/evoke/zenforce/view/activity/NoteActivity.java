package com.evoke.zenforce.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.NoteEntityBean;
import com.evoke.zenforce.model.database.dao.NoteDAO;

public class NoteActivity extends AppCompatActivity {


    private static final String TAG = "NoteActivity";
    private long mVisitId;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditText = (EditText) findViewById(R.id.etNote);
        extractDataFromBundle();
    }

    private void extractDataFromBundle() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mVisitId = bundle.getLong("mVisitId");
            getSupportActionBar().setTitle(bundle.getString("visitName"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_upload_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_submit:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String note = mEditText.getText().toString();

        if (note != null && TextUtils.isEmpty(note.trim())) {
            Toast.makeText(this, "note is empty!!", Toast.LENGTH_LONG).show();
            return;
        }



        Log.v(TAG, "  entered text : " + note);
        Log.v(TAG, "  mVisitId : " + mVisitId);

        NoteEntityBean bean = new NoteEntityBean();
        bean.setNote(note);
        bean.setVisitId(mVisitId);
        NoteDAO dao = NoteDAO.getSingletonInstance(this);
        long noteRowId = dao.insert(bean);
        Log.d(TAG, " note inserted id " + noteRowId);

        if (noteRowId > 0) {
//            String selection = DbConstants.NoteTable.COLUMN_VISIT_ID + " = ?";
//            String[] selectionArgs = new String[] {String.valueOf(mVisit_id)};
//            int updateId = dao.update(bean, selection, selectionArgs);
            finish();
        }
    }
}
