package com.evoke.zenforce.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.pojo.NavigationItem;

import java.util.List;

/**
 * Created by spinninti on 11/9/2016.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    private static final String TAG = "NavigationDrawerAdapter";
    private Context mContext;
    private List<NavigationItem> mItemList;
    private NavigationOnClickListener mOnClickListener;

    private int mSelectedPos = 0;
    private TextView mPrevTextView;

    public interface NavigationOnClickListener {
        void onNavigationItemClick(NavigationItem item);
    }

    public NavigationDrawerAdapter(Context context, List<NavigationItem> itemList, NavigationOnClickListener onClickListener) {
        mContext                = context;
        mItemList               = itemList;
        mOnClickListener        = onClickListener;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public NavigationItem getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final MyViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.navigation_item, null);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }

        final NavigationItem item = getItem(position);
        if (item != null && holder != null) {
            holder.nav_view.setBackgroundColor(item.getColor());
            holder.nav_image.setImageResource(item.getDrawable());
            holder.nav_text.setText(item.getName());
            if (position == 0) {
                holder.nav_text.setTextColor(item.getColor());
            } /*else {
                holder.text.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            }*/

        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.nav_text.setTextColor(item.getColor());
                mOnClickListener.onNavigationItemClick(item);
            }
        });

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, " onClick.." + item.getName());

                if (mPrevTextView == null) {
                    mPrevTextView = holder.nav_text;
                }

                if (mPrevTextView.getTag() != null) {
                   TextView tv = (TextView) mPrevTextView.getTag();
                    tv.setTextColor(item.getDefaultTextColor());
                }
                mPrevTextView.setTag(holder.nav_text);
                holder.nav_text.setTextColor(item.getColor());
                v.setTag(item);
                mOnClickListener.onClick(v);
            }
        });*/

        return view;
    }

   public class MyViewHolder extends RecyclerView.ViewHolder {
       View nav_view;
       ImageView nav_image;
       TextView nav_text;

       public MyViewHolder(View view) {
           super(view);
           nav_view = view.findViewById(R.id.view_color);
           nav_image = (ImageView) view.findViewById(R.id.nav_image);
           nav_text = (TextView) view.findViewById(R.id.item_name);
       }

   }
}
