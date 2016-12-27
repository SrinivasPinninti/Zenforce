package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.pojo.nearby.Result;

import java.util.List;

/**
 * Created by spinninti on 11/12/2016.
 */
public class NewPlacesListAdapter extends RecyclerView.Adapter<NewPlacesListAdapter.PlaceViewHolder> {


    private List<Result> mResultList;
    private Context mContext;


    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Result result);
    }

    public NewPlacesListAdapter(Context context, List<Result> resultList, OnItemClickListener onItemClickListener) {
        mContext = context;
        mResultList = resultList;
        mOnItemClickListener = onItemClickListener;
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
         public void bindView(final Result result) {
             placeName.setText(result.getName());
             address.setText(result.getVicinity());
             mItemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     mOnItemClickListener.onItemClick(result);
                 }
             });
         }



     }



}
