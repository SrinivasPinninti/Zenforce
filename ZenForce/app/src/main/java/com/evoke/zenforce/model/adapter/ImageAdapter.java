package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.evoke.zenforce.R;

import java.util.ArrayList;

/**
 * Created by spinninti on 1/7/2017.
 */
public class ImageAdapter extends PagerAdapter{


    private Context context;
    private ArrayList<String> images;

    public ImageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.image, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        String imagePath = images.get(position);



        return super.instantiateItem(container, position);
    }
}
