package com.evoke.zenforce.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.PhotoEntityBean;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.database.dao.PhotoDAO;
import com.evoke.zenforce.model.database.dao.VisitDAO;
import com.evoke.zenforce.utility.Util;
import com.evoke.zenforce.view.chip.Chip;
import com.evoke.zenforce.view.chip.ChipViewAdapter;
import com.evoke.zenforce.view.chip.MainChipViewAdapter;
import com.evoke.zenforce.view.chip.OnChipClickListener;
import com.evoke.zenforce.view.chip.Tag;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements  OnChipClickListener{

    private static final String TAG = "PhotoActivity";

    private Uri fileUri; // file url to store image/video

    private ImageView imageView;
    private EditText etImageNote;


    private String mImagePath;
    private List<Chip> mTagList1;
    private com.evoke.zenforce.view.chip.ChipView mTextChipLayout;
//    private long mVisitId;
    private Uri file;

    private int currentApiVersion;

    private long mVisitId;

    private HashMap<String, String> qaMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Photo");

        initUI();


       Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mVisitId = bundle.getLong("visitId");
            Log.d(TAG, "mVisitId : " + mVisitId);
        }

        currentApiVersion = android.os.Build.VERSION.SDK_INT;


        Log.v(TAG, "currentApiVersion : " + currentApiVersion);

        if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.v(TAG, " Marshmallow......");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }

        }



        Log.v(TAG, " onCreate bundle : " + savedInstanceState);
        if (savedInstanceState == null) {  // Only first time capture image
            takePhoto();
        }
    }

    private void initUI() {

        imageView = (ImageView) findViewById(R.id.img);
        mTagList1 = new ArrayList<>();
        mTagList1.add(new Tag("business", 1));
        mTagList1.add(new Tag("movies", 2));
        mTagList1.add(new Tag("sports", 2));
        mTagList1.add(new Tag("engineering", 2));
        mTagList1.add(new Tag("education", 2));

        // Adapter
        ChipViewAdapter adapterLayout = new MainChipViewAdapter(this);

        // Custom layout and background colors
        mTextChipLayout = (com.evoke.zenforce.view.chip.ChipView) findViewById(R.id.text_chip_layout);
        mTextChipLayout.setAdapter(adapterLayout);
        mTextChipLayout.setChipList(mTagList1);
        mTextChipLayout.setOnChipClickListener(this);

        etImageNote = (EditText) findViewById(R.id.etImageNote);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume..");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause..");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, " onSaveInstanceState..." + outState);
        outState.putParcelable("file_uri", fileUri);
        outState.putBoolean("isPhoto_uploaded", true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(TAG, " onRestoreInstanceState...." + savedInstanceState);
        if (savedInstanceState != null) {
            fileUri = savedInstanceState.getParcelable("file_uri");
            Log.v(TAG, " fileUri : " + fileUri);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, " onDestroy called....");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
//                    permissionGranted = true;
//                    Toast.makeText(this, "Permission Granted!!!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " Permission Granted!!!!");
                } else {
//                    permissionGranted = false;
//                    Toast.makeText(this, "No permission!!!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " No permission!!!!");
                }

                break;
        }
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ZenForceDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private void takePhoto() {
        Log.d(TAG, " takePhoto called....");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                mImagePath = file.getPath();
                Log.v(TAG, " set Image......");
                if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Log.v(TAG, "Marshmallow....");
                    imageView.setImageURI(file);
                } else {
                    Log.v(TAG, "Lollipop....");
                    previewCapturedImage();
                }
            }
        }
    }

    private void previewCapturedImage() {
        try {

            Bitmap bitmap1 = Util.getScaledImage(mImagePath);
            imageView.setImageBitmap(bitmap1);
        } catch (NullPointerException e) {
            e.printStackTrace();
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
                savePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void savePhoto() {

        StringBuilder sb = new StringBuilder();

        int size = mTagList1.size();

        for (int i=0; i<size; i++) {
            Chip tag = mTagList1.get(i);
            if (tag.getType() == 1) {
                sb.append("#" + tag.getText());
                if (++i < size) sb.append( ", ");
            }

        }

        String tags = sb.length() > 0 ? sb.substring(0, sb.length() - 1): "";

        String imageNote = etImageNote.getText().toString().trim();

//        Log.d(TAG, " sVisitId : " + Util.sVisitId);


        Log.d(TAG, " onSave mVisitId : " + mVisitId);
        Log.d(TAG, " mImagePath : " + mImagePath);
        Log.d(TAG, " imageNote : " + imageNote);

        PhotoEntityBean photo = new PhotoEntityBean();
        photo.setVisitId(mVisitId);
        photo.setPath(mImagePath);
        photo.setComment(imageNote);
        photo.setTag(tags);

        PhotoDAO dao = PhotoDAO.getSingletonInstance(this);
        long photoRowId = dao.insert(photo);
        Log.v(TAG, " insert id : " + photoRowId);
        if (photoRowId > 0) {


            Util.sPhoto_count ++;
            VisitDAO visitDAO = VisitDAO.getSingletonInstance(this);
            VisitBean bean = new VisitBean();

            Log.d(TAG, " Util.sPhoto_count : " + Util.sPhoto_count);
            Log.d(TAG, " photoRowId : " + photoRowId);

            bean.setImagePath(mImagePath);
            bean.setImageCount(Util.sPhoto_count);
            bean.setPhotoId(photoRowId);

            String selection = DbConstants.VisitTable.COLUMN_ID + " =?";
            String[] selectionArgs = new String[] { String.valueOf(mVisitId) };

            long id = visitDAO.update(bean, selection, selectionArgs);

            Log.d(TAG, " visit table updated id : " + id);
            Intent intent = new Intent();
            intent.putExtra("imagePath", mImagePath);
            setResult(Activity.RESULT_OK, intent);
            finish();

        } else {
            Log.e(TAG, " error in inserting record...");
        }

    }

    @Override
    public void onChipClick(Chip chip) {
        for (int i = 0; i < mTagList1.size(); i++) {
            Tag mTag = (Tag) mTagList1.get(i);
            if (mTag.getText().equals(chip.getText())) {
                int type = mTag.getType();
                Tag t;
                if (type == 1) {
                    t = new Tag(mTag.getText(), 2);
                } else {
                    t = new Tag(mTag.getText(), 1);
                }

                mTagList1.remove(mTag);
                mTagList1.add(i, t);
                mTextChipLayout.setChipList(mTagList1);
                mTextChipLayout.setOnChipClickListener(this);

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




}
