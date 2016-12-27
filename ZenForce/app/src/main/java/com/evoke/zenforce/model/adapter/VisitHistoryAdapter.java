package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.NoteEntityBean;
import com.evoke.zenforce.model.database.beanentity.PhotoEntityBean;

import java.util.List;

/**
 * Created by spinninti on 12/14/2016.
 */
public class VisitHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final String TAG = "VisitHistoryAdapter";


    private int mPhotoCount = 0;
    private int mNoteCount = 0;

    private Context context;
    private List<BaseEntityBean> beanList;
    private boolean isHeader;
    private boolean isFooter;

    public VisitHistoryAdapter(Context context) {
        Log.v(TAG, "VisitHistoryAdapter...." );
        this.context = context;

    }

    public void setData(List<BaseEntityBean> beanList, boolean isHeader, boolean isFooter) {
        this.beanList = beanList;
        this.isHeader = isHeader;
        this.isFooter = isFooter;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.v(TAG, "" + viewType);
        if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(context).inflate(R.layout.history_header, parent, false);
            return new HeaderViewHolder(v);

        } else if (viewType == TYPE_ITEM) {

            View v = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
            return new ItemViewHolder(v);
        }

        return null;
    }

    private BaseEntityBean getItem (int position) {
        return beanList.get (position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof HeaderViewHolder) {
            Log.v(TAG, "HeaderViewHolder...");
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.bindView("LAST 7 DAYS");
           /* headerHolder.txtTitleHeader.setText ("LAST 7 DAYS");
            headerHolder.txtTitleHeader.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {
                    Toast.makeText (context, "Clicked Header", Toast.LENGTH_SHORT).show ();
                }
            });*/
        } else if(holder instanceof ItemViewHolder) {
            Log.v(TAG, "ItemViewHolder...");
            BaseEntityBean currentItem = getItem (position - 1);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.bindView(currentItem);

           /* itemViewHolder.tvDate.setText (currentItem.getName ());
            itemViewHolder.tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clicked item" + (position-1), Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }

    @Override
    public int getItemCount() {

        int itemCount =  beanList.size();

        if (isHeader) {
            itemCount = itemCount + 1;
        }

        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }



    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;

        public HeaderViewHolder (View itemView) {
            super (itemView);
            tvHeader = (TextView) itemView.findViewById (R.id.tvHeader);
        }

        public void bindView(String header) {

            tvHeader.setText(header);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvImageNumber;
        TextView tvNotesNumber;
        TextView tvVisitNumber;

        public ItemViewHolder (View itemView) {
            super (itemView);
            tvDate = (TextView) itemView.findViewById (R.id.tvDate);
            tvImageNumber = (TextView) itemView.findViewById (R.id.tvImageNumber);
            tvNotesNumber = (TextView) itemView.findViewById (R.id.tvNotesNumber);
            tvVisitNumber = (TextView) itemView.findViewById (R.id.tvVisitNumber);
        }

        public void bindView(BaseEntityBean bean) {
            Log.v(TAG, "bindView..." + bean);
            if (bean instanceof PhotoEntityBean) {

                PhotoEntityBean photo = (PhotoEntityBean) bean;
                mPhotoCount = mPhotoCount + 1;
                Log.v(TAG, " mPhotoCount : " + mPhotoCount);
                tvImageNumber.setVisibility(View.VISIBLE);
                tvImageNumber.setText(mPhotoCount +"");

            } else if (bean instanceof NoteEntityBean) {

                NoteEntityBean note = (NoteEntityBean) bean;
                mNoteCount = mNoteCount + 1;
                Log.v(TAG, " mNoteCount : " + mNoteCount);
                tvNotesNumber.setVisibility(View.VISIBLE);
                tvNotesNumber.setText(mNoteCount +"");

            } else {

//                VisitEntityBean visit = (VisitEntityBean) bean;
//                Log.v(TAG, " set date " + visit.getCreated_at());
//                tvDate.setVisibility(View.VISIBLE);
//                Log.d(TAG, " formatted date " + Util.getDateTime(visit.getCreated_at()));
//                tvDate.setText(Util.getDateTime(visit.getCreated_at()));
            }



        }
    }


 }
