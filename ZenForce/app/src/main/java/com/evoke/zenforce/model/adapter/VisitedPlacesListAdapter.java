package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.PlaceBean;
import com.evoke.zenforce.view.activity.VisitActivity;

import java.util.List;

/**
 * Created by spinninti on 11/21/2016.
 */
public class VisitedPlacesListAdapter extends RecyclerView.Adapter<VisitedPlacesListAdapter.PlaceViewHolder> {


    private static final String TAG = "VisitedListAdapter";
    private List<PlaceBean> mResultList;
    private Context mContext;



    public VisitedPlacesListAdapter(Context context, List<PlaceBean> resultList) {
        mContext = context;
        mResultList = resultList;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_row, parent, false);
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

        private LinearLayout cardItem;
        private TextView placeName;
        private TextView address;

        private View mItemView;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            cardItem = (LinearLayout) itemView.findViewById(R.id.cardItem);
            placeName = (TextView) itemView.findViewById(R.id.tvPlaceName);
            address   = (TextView) itemView.findViewById(R.id.tvPlaceAddress);

        }
        public void bindView(final PlaceBean place) {
            placeName.setText(place.getName());
            address.setText(place.getAddress());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, " call VisitActivity...." );
                    Intent myPlacesIntent = new Intent(mContext, VisitActivity.class);
                    myPlacesIntent.putExtra("placeBean", place);
                    myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(myPlacesIntent);
                }
            });
        }



    }



}
