package com.evoke.zenforce.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.PhotoEntityBean;
import com.evoke.zenforce.model.database.dao.PhotoDAO;
import com.evoke.zenforce.view.chip.Chip;
import com.evoke.zenforce.view.chip.ChipViewAdapter;
import com.evoke.zenforce.view.chip.MainChipViewAdapter;
import com.evoke.zenforce.view.chip.OnChipClickListener;
import com.evoke.zenforce.view.chip.Tag;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements OnChipClickListener{

    private static final String TAG = "PhotoActivity";

    private Uri fileUri; // file url to store image/video

    private ImageView imageView;
    private String mImagePath;
    private List<Chip> mTagList1;
    private com.evoke.zenforce.view.chip.ChipView mTextChipLayout;
    private long mVisit_id;
    private Uri file;

    private int currentApiVersion;

    public interface PhotoInsertedCallBack {

        void photoInserted(long rowId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
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

        initUI();
        extractDataFromBundle();

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
    }

    private void extractDataFromBundle() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mVisit_id = bundle.getLong("visit_id");
            mImagePath  = bundle.getString("imagePath");

            getSupportActionBar().setTitle(bundle.getString("visitName"));
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
//                    permissionGranted = true;
                    Toast.makeText(this, "Permission Granted!!!!", Toast.LENGTH_SHORT).show();
                } else {
//                    permissionGranted = false;
                    Toast.makeText(this, "No permission!!!!", Toast.LENGTH_SHORT).show();
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
        Log.v(TAG, " takePhoto called....");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
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

            Bitmap bitmap1 = getCameraPhotoOrientation(file.getPath());
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
        for (Chip tag : mTagList1) {
           if (tag.getType() == 1) {
               sb.append(tag.getText() + ",");
           }
        }
        String tags = sb.length() > 0 ? sb.substring(0, sb.length() - 1): "";



        Log.v(TAG, " mVisit_id : " + mVisit_id);

        PhotoEntityBean bean = new PhotoEntityBean();
        bean.setVisitId(mVisit_id);
        bean.setPath(mImagePath);
        bean.setTag(tags);

        PhotoDAO dao = PhotoDAO.getSingletonInstance(this);
        long photoRowId = dao.insert(bean);
        Log.v(TAG, " insert id : " + photoRowId);
        if (photoRowId > 0) {
//            Log.v(TAG, " return back.... ");
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra("photoRowId", photoRowId);
//            setResult(RESULT_OK, returnIntent);
//
            Log.v(TAG, " return back.... ");
//            ZenForceApplication.getInstance().getCallback().photoInserted(photoRowId);
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



    public Bitmap getCameraPhotoOrientation(String imagePath){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();
        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;
        final Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        Bitmap bmRotated = rotateBitmap(bitmap, orientation);
        return bmRotated;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}
