package com.evoke.zenforce.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.PhotoEntityBean;
import com.evoke.zenforce.model.database.dao.PhotoDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    public static final String TAG = "ImageActivity";

    private static final int PHOTO_LOADER_ID = 555;

    private ArrayList<String> mImagePaths = null;

    private ViewPager mViewPager;
//    private ImageView imgDot;
    private List<ImageView> dotImageViews;
    private int image_number = 0;

    private long mVisitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mVisitId   = bundle.getLong("VISIT_ID", 0);
        }

        Log.d(TAG, " mVisitId : " + mVisitId);
        getSupportLoaderManager().initLoader(0, null, this);
        mViewPager  = (ViewPager) findViewById(R.id.view_pager);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        PhotoDAO dao = PhotoDAO.getSingletonInstance(ImageActivity.this);
        Log.d(TAG, "mLoader mVisitId : " + mVisitId);
        String selection = DbConstants.PhotoTable.COLUMN_VISIT_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(mVisitId)};

        return new CursorLoader(ImageActivity.this, dao.getURI(), null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished.....");
        if (cursor != null && cursor.moveToFirst()) {
            PhotoDAO dao = PhotoDAO.getSingletonInstance(ImageActivity.this);
            List<PhotoEntityBean> photoBeanList = new ArrayList<>();

            do {

                PhotoEntityBean photoBean = (PhotoEntityBean) dao.populate(cursor);
                photoBeanList.add(photoBean);

            } while (cursor.moveToNext());



            image_number = photoBeanList.size();
            Log.d(TAG, "image_number : " + image_number);

            addDots();

            FullScreenImageAdapter imageAdapter = new FullScreenImageAdapter(ImageActivity.this, photoBeanList);
            mViewPager.setAdapter(imageAdapter);
            mViewPager.setCurrentItem(photoBeanList.size());
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    /*if (position < dots.length) {
                        imgDot.setImageResource(dots[position]);
                    } else {
                        imgDot.setImageResource(dots[position % dots.length]);
                    }*/
                    Log.e(TAG, "onPageScrolled position : " + position);
                    selectDot(position);


                }

                @Override
                public void onPageSelected(int position) {
                    Log.e(TAG, "onPageSelected position : " + position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void addDots() {
        dotImageViews = new ArrayList<>();
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        for (int i = 0; i < image_number; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(getResources().getDrawable(R.drawable.tab_indicator_default));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            dotsLayout.addView(dot, params);
            dotImageViews.add(dot);
        }
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for(int i = 0; i < image_number; i++) {
            int drawableId = (i==idx)?(R.drawable.tab_indicator_selected):(R.drawable.tab_indicator_default);
            Drawable drawable = res.getDrawable(drawableId, null);
            dotImageViews.get(i).setImageDrawable(drawable);
        }
    }



    public class FullScreenImageAdapter extends PagerAdapter {

        private Context context;
        private List<PhotoEntityBean> photos;

        public FullScreenImageAdapter(Context context, List<PhotoEntityBean> photos) {
            this.context = context;
            this.photos = photos;
            Log.d(TAG, "FullScreenImageAdapter...." + photos.size());
        }

        @Override
        public int getCount() {
            return this.photos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.photo_fullscreen, container, false);

            ImageView   imageView       = (ImageView) v.findViewById(R.id.image);
            TextView    image_tags      = (TextView) v.findViewById(R.id.image_tags);
            TextView    image_text      = (TextView) v.findViewById(R.id.image_text);
            ImageView   image_chat      = (ImageView) v.findViewById(R.id.image_chat);


            final PhotoEntityBean photo = photos.get(position);

            if (photo != null) {
                String imagePath = photo.getPath();

                if (imagePath != null) {

                    File imgFile = new File(imagePath);
                    if (imgFile != null && imgFile.exists()) {


              /*  Picasso.with(getContext())
                        .load(Uri.fromFile(imgFile))
                        .placeholder(R.drawable.image_placeholder)
                        .into(imageView);*/

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        options.inDither = false;
                        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options );
                        imageView.setImageBitmap(bitmap);
                        //set to image view
                        imageView.setImageBitmap(bitmap);

                    }

                }

                image_tags.setText(photo.getTag());
                image_text.setText(photo.getComment());
                image_chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ImageActivity.this, ChatActivity.class);
                        intent.putExtra("itemID" , photo.get_ID());
                        startActivity(intent);
                    }
                });
            }
            ((ViewPager) container).addView(v);

            return v;
        }




        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((LinearLayout) object);

        }
    }
}
