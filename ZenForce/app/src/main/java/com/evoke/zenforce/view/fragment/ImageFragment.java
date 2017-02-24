package com.evoke.zenforce.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.evoke.zenforce.R;

import java.io.File;

public class ImageFragment extends Fragment {

    private String mImagePath;

    private  Bitmap bitmap;


    public static ImageFragment getInstance(String imagePath) {
        ImageFragment f = new ImageFragment();
        Bundle args = new Bundle();
        args.putString("image_path", imagePath);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePath = getArguments().getString("image_path");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        if (mImagePath != null) {

                File imgFile = new File(mImagePath);
                if (imgFile != null && imgFile.exists()) {


              /*  Picasso.with(getContext())
                        .load(Uri.fromFile(imgFile))
                        .placeholder(R.drawable.image_placeholder)
                        .into(imageView);*/

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    options.inDither = false;
                    bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options );
                    imageView.setImageBitmap(bitmap);
                    //set to image view
                    imageView.setImageBitmap(bitmap);

                }

        }

      /*  BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 4;
        o.inDither = false;
        bitmap = BitmapFactory.decodeResource(getResources(), imageResource, o);
        imageView.setImageBitmap(bitmap);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}