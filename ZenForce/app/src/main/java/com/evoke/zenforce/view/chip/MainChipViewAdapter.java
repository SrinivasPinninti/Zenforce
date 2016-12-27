package com.evoke.zenforce.view.chip;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.evoke.zenforce.R;

/**
 * Created by spinninti on 11/28/2016.
 */
public class MainChipViewAdapter extends ChipViewAdapter {

    private Context mContext;
    public MainChipViewAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int getLayoutRes(int position) {
        Tag tag = (Tag) getChip(position);

        switch (tag.getType()) {

            case 2:
                return R.layout.chip_view_untagged;


            case 1:
                return R.layout.chip_view_tagged;

            default: return 0;
        }
    }

    @Override
    public int getBackgroundColor(int position) {
        Tag tag = (Tag) getChip(position);

        switch (tag.getType()) {


            case 1:
                return getColor(R.color.colorAccent);



            default:
                return 0;
        }
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return getColor(R.color.launch_primary_dark);
    }

    @Override
    public int getBackgroundRes(int position) {


        return 0;

    }

    @Override
    public void onLayout(View view, int position) {
        Tag tag = (Tag) getChip(position);


        TextView txt =     ((TextView) view.findViewById(android.R.id.text1));
//        txt.setTypeface(Utils.getRobotoRegularFont(mContext));
    }
}