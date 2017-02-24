package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.NoteEntityBean;
import com.evoke.zenforce.model.database.beanentity.PhotoEntityBean;

import java.io.File;
import java.util.List;

/**
 * Created by spinninti on 11/21/2016.
 */
public class VisitedHistoryListAdapter extends RecyclerView.Adapter<VisitedHistoryListAdapter.PlaceViewHolder> {


    private static final String TAG = "VisitedListAdapter";

    private static final  int THUMB_SIZE = 240;

    private List<List<? extends BaseEntityBean>> mResultList;
    private Context mContext;



    private int photo_count = 0;
    private int note_count = 0;

    String[] keys = new String[] { "PHOTO", "NOTE", "TIMER" };



    public VisitedHistoryListAdapter(Context context, List<List<? extends BaseEntityBean>> resultList) {
        mContext = context;
        mResultList = resultList;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        List<? extends BaseEntityBean> list = mResultList.get(position);
        Log.d(TAG, " mResultList size : " + list.size());


        for (BaseEntityBean base : list) {

            if (base instanceof PhotoEntityBean) {

                while (photo_count < list.size()) {
                    photo_count++;
                }
                Log.d(TAG, " photo_count : " + photo_count);
                holder.photoBindView((PhotoEntityBean) base);
            } else if (base instanceof NoteEntityBean) {

                while (note_count < list.size()) {
                    note_count++;
                }
                Log.d(TAG, " note_count : " + note_count);
                holder.noteBindView((NoteEntityBean) base);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;

        ImageView photoImg;
        TextView photoCount;
//        TextView tvNotesNumber;
//        TextView tvVisitNumber;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById (R.id.tvDate);
            photoImg = (ImageView) itemView.findViewById (R.id.photoImg);
            photoCount = (TextView) itemView.findViewById (R.id.photoCount);
//            tvImageNumber = (TextView) itemView.findViewById (R.id.tvImageNumber);
//            tvNotesNumber = (TextView) itemView.findViewById (R.id.tvNotesNumber);
//            tvVisitNumber = (TextView) itemView.findViewById (R.id.tvVisitNumber);

        }
        public void photoBindView(PhotoEntityBean photo) {

                Log.d(TAG, " photo_count : " + photo_count);
                Log.d(TAG, " photo path : " + photo.getPath());

            photoCount.setText(photo_count+"");

                File imgFile = new File(photo.getPath().toString());
                if(imgFile.exists()){
                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imgFile.getAbsolutePath()),
                            THUMB_SIZE, THUMB_SIZE);
                    Log.d(TAG, "ThumbImage :  " + ThumbImage);
                    photoImg.setImageBitmap(ThumbImage);

                }
        }

        public void noteBindView(NoteEntityBean note) {


        }



    }



}
