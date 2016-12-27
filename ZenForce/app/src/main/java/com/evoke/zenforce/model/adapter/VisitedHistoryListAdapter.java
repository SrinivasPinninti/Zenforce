package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evoke.zenforce.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by spinninti on 11/21/2016.
 */
public class VisitedHistoryListAdapter extends RecyclerView.Adapter<VisitedHistoryListAdapter.PlaceViewHolder> {


    private static final String TAG = "VisitedListAdapter";
    private List<Map<String, Integer>> mResultList;
    private Context mContext;

    String[] keys = new String[] { "PHOTO", "NOTE", "TIMER" };



    public VisitedHistoryListAdapter(Context context, List<Map<String, Integer>> resultList) {
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
        Map<String, Integer> map = mResultList.get(position);
        holder.bindView(map);
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvImageNumber;
        TextView tvNotesNumber;
        TextView tvVisitNumber;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById (R.id.tvDate);
            tvImageNumber = (TextView) itemView.findViewById (R.id.tvImageNumber);
            tvNotesNumber = (TextView) itemView.findViewById (R.id.tvNotesNumber);
            tvVisitNumber = (TextView) itemView.findViewById (R.id.tvVisitNumber);

        }
        public void bindView(Map<String, Integer> map) {

            if (map == null) return;

            Set<String> keys = map.keySet();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {

                String key = iterator.next();

                if ("PHOTO".equals(key)) {

                    Log.v(TAG, " photo count : " + map.get(key));
                    tvImageNumber.setVisibility(View.VISIBLE);
                    tvImageNumber.setText(map.get(key) +"");

                } else if ("NOTE".equals(key)) {

                    Log.v(TAG, " note count : " + map.get(key));
                    tvNotesNumber.setVisibility(View.VISIBLE);
                    tvNotesNumber.setText(map.get(key) +"");

                } else if ("TIMER".equals(key)) {

                    Log.v(TAG, " visit count : " + map.get(key));
                    tvVisitNumber.setVisibility(View.VISIBLE);
                    tvVisitNumber.setText(map.get(key) +"");

                }


            }





//            placeName.setText(photo.getPath());
           /* mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, " call VisitActivity...." );
                    Intent myPlacesIntent = new Intent(mContext, VisitActivity.class);
                    myPlacesIntent.putExtra("visitBean", visit);
                    myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(myPlacesIntent);
                }
            });*/
        }



    }



}
