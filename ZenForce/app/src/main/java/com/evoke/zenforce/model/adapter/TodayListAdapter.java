package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.utility.Util;
import com.evoke.zenforce.view.activity.ImageActivity;
import com.evoke.zenforce.view.activity.NoteDisplayActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by spinninti on 11/21/2016.
 */
public class TodayListAdapter extends RecyclerView.Adapter<TodayListAdapter.PlaceViewHolder> {


    private static final String TAG = "TodayListAdapter";
    private List<VisitBean> mResultList;
    private Context mContext;



    public TodayListAdapter(Context context, List<VisitBean> resultList) {
        mContext = context;
        mResultList = resultList;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_row_today, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        holder.bindView(mResultList.get(position));
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView time;
        private TextView duration;
        private TextView address;
        private RelativeLayout  rl_image;
        private RelativeLayout  rl_note;
        private ImageView imageView;
        private TextView  count;
        private TextView  tvNotes;
        private TextView  today_note_count;

        private View mItemView;

        private String mImagePath;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            name        = (TextView) itemView.findViewById(R.id.name);
            time        = (TextView) itemView.findViewById(R.id.time);
            duration    = (TextView) itemView.findViewById(R.id.duration);
            address     = (TextView) itemView.findViewById(R.id.address);

            rl_image    = (RelativeLayout) itemView.findViewById(R.id.rl_image);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            count       = (TextView) itemView.findViewById(R.id.count);

            rl_note         = (RelativeLayout) itemView.findViewById(R.id.rl_note);
            tvNotes         = (TextView) itemView.findViewById(R.id.notes);
            today_note_count     = (TextView) itemView.findViewById(R.id.today_note_count);

//            rl_image.setOnClickListener(onImageClickListener);

        }
        public void bindView(final VisitBean visit) {

            long startTime  = visit.getStart_time();
            long endTime    = visit.getEnd_time();

//            long duration = Math.abs(visit.getStart_time() - visit.getEnd_time());
            String visitDuration = Util.getVisitPeriod(startTime, endTime);
            String timeStamp = Util.getTimeStamp(startTime, endTime);

            Log.d(TAG, "visit duration :  " + visitDuration);
            Log.d(TAG, "visit timeStamp :  " + timeStamp);

            name.setText(visit.getName());
            duration.setText("Duration : " + visitDuration);
            time.setText(timeStamp);
            address.setText(visit.getAddress());

            int imageCount = visit.getImageCount();

            count.setText(imageCount > 1 ? imageCount + " photos" : " ");


            // Image related

            mImagePath = visit.getImagePath();

            if (mImagePath != null) {

                File imgFile = new File(mImagePath);
                if (imgFile != null && imgFile.exists()) {

                    if (rl_image.getVisibility() == View.GONE) {
                        rl_image.setVisibility(View.VISIBLE);
                    }

                    Picasso.with(mContext)
                            .load(Uri.fromFile(imgFile))
                            .placeholder(R.drawable.placeholder)
                            .into(imageView);

                    rl_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.v(TAG, " call ImageActivity...." );
                            Intent intent = new Intent(mContext, ImageActivity.class);
                            intent.putExtra("IMG_PATH", mImagePath);
                            intent.putExtra("VISIT_ID", visit.get_ID());
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        }
                    });


                }

            } else {
                Log.e(TAG, " image not available.....");
                rl_image.setVisibility(View.GONE);
            }


            // Notes related

            String notes = visit.getNote();

            if (notes != null && !TextUtils.isEmpty(notes)) {

                if (rl_note.getVisibility() == View.GONE) {
                    rl_note.setVisibility(View.VISIBLE);
                }

                tvNotes.setText(notes);
                today_note_count.setText(visit.getNoteCount() + " notes");

                rl_note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v(TAG, " call NoteActivity...." );
                        Intent intent = new Intent(mContext, NoteDisplayActivity.class);
                        intent.putExtra("VISIT_ID", visit.get_ID());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);
                    }
                });

            } else {
                Log.e(TAG, " notes not available.....");
                rl_note.setVisibility(View.GONE);
            }





        }



       /* public View.OnClickListener onImageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("IMG_PATH", mImagePath);
                mContext.startActivity(intent);


            }
        };*/



    }



}
