package com.evoke.zenforce.view.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.NoteEntityBean;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.database.dao.NoteDAO;
import com.evoke.zenforce.model.database.dao.VisitDAO;
import com.evoke.zenforce.utility.Util;

public class NoteActivity extends AppCompatActivity {


    private static final String TAG = "NoteActivity";
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
        getSupportActionBar().setTitle("Add Note");

        mEditText = (EditText) findViewById(R.id.etNote);
//        extractDataFromBundle();
    }

   /* private void extractDataFromBundle() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            mVisitId = bundle.getLong("mVisitId");
            getSupportActionBar().setTitle(bundle.getString("visitName"));
        }

    }*/

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
            Toast.makeText(this, "Note is empty!!", Toast.LENGTH_LONG).show();
            return;
        }



        Log.v(TAG, "  entered note : " + note);
        Log.d(TAG, " sVisitId : " + Util.sVisitId);

        NoteEntityBean bean = new NoteEntityBean();
        bean.setNote(note);
        bean.setVisitId(Util.sVisitId);
        NoteDAO dao = NoteDAO.getSingletonInstance(this);
        long noteRowId = dao.insert(bean);
        Log.d(TAG, " note inserted id " + noteRowId);

        if (noteRowId > 0) {
//       Log.v(TAG, " Util.sPhoto_count : " + Util.sPhoto_count);

            Util.sNote_count ++;

            Log.v(TAG, "sNote_count : " + Util.sNote_count);

            VisitDAO visitDAO = VisitDAO.getSingletonInstance(this);

            VisitBean visit = new VisitBean();

            visit.setNote(note);
            visit.setNoteCount(Util.sNote_count);

            String selection = DbConstants.VisitTable.COLUMN_ID + " =?";
            String[] selectionArgs = new String[] { String.valueOf(Util.sVisitId) };

            long id = visitDAO.update(visit, selection, selectionArgs);

            Log.d(TAG, " updated id : " + id);

//            note.set_ID(noteRowId);
//            ZenForceApplication.getInstance().getPhotoCallBack().addPhoto(photo);
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();

        } else {
            Log.e(TAG, " error in inserting note record...");
        }
    }
}
