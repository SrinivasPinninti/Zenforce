package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.view.activity.VisitActivity;

import java.util.List;

/**
 * Created by spinninti on 11/21/2016.
 */
public class VisitListAdapter extends ArrayAdapter<VisitBean> {


    private static final String TAG = "VisitedListAdapter";
    private List<VisitBean> mResultList;
    private Context mContext;

    private LayoutInflater mInflater;

    public VisitListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<VisitBean> data) {
        clear();
        if (data != null) {
            for (VisitBean appEntry : data) {
                add(appEntry);
            }
        }
    }


    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public VisitBean getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Holder holder;

        if (view == null) {
            view = mInflater.inflate(R.layout.row_visit, parent, false);
            holder = new Holder(view);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        VisitBean bean = mResultList.get(position);
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getAddress());

        return view;
    }

    public static class Holder {

        private TextView name;
        private TextView address;

        public Holder(View v) {
            name    = (TextView) v.findViewById(R.id.name);
            address = (TextView) v.findViewById(R.id.address);

        }


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
        public void bindView(final VisitBean visit) {
            placeName.setText(visit.getName());
            address.setText(visit.getAddress());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, " call VisitActivity...." );
                    Intent myPlacesIntent = new Intent(mContext, VisitActivity.class);
                    myPlacesIntent.putExtra("visitBean", visit);
                    myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(myPlacesIntent);
                }
            });
        }



    }



}
