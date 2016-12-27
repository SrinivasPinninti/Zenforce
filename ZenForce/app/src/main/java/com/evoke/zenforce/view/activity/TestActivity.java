package com.evoke.zenforce.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.evoke.zenforce.R;
import com.evoke.zenforce.view.chip.Chip;
import com.evoke.zenforce.view.chip.ChipView;
import com.evoke.zenforce.view.chip.ChipViewAdapter;
import com.evoke.zenforce.view.chip.MainChipViewAdapter;
import com.evoke.zenforce.view.chip.OnChipClickListener;
import com.evoke.zenforce.view.chip.Tag;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements OnChipClickListener {
    private ChipView mTextChipAttrs;
//    private ChipView chipCustomLayout;

    private ChipView mTextChipLayout;
    private  List<Chip> mTagList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chip);
//      // Attrs ChipTextView

        mTagList1 = new ArrayList<>();
        mTagList1.add(new Tag("business", 1));
        mTagList1.add(new Tag("movies", 2));
        mTagList1.add(new Tag("sports", 2));
        mTagList1.add(new Tag("engineering", 2));
        mTagList1.add(new Tag("education", 2));


        // Adapter
        ChipViewAdapter adapterLayout = new MainChipViewAdapter(this);
        ChipViewAdapter adapterOverride = new MainChipViewAdapter(this);

        // Custom layout and background colors
        mTextChipLayout = (ChipView) findViewById(R.id.text_chip_layout);
        mTextChipLayout.setAdapter(adapterLayout);
//        mTextChipLayout.setChipLayoutRes(R.layout.chip_close);
//        mTextChipLayout.setChipBackgroundColor(getResources().getColor(R.color.launch_primary));
//        mTextChipLayout.setChipBackgroundColorSelected(getResources().getColor(R.color.launch_primary_dark));
        mTextChipLayout.setChipList(mTagList1);
        mTextChipLayout.setOnChipClickListener(this);

    }


    @Override
    public void onChipClick(Chip chip) {
//
        for (int i = 0; i < mTagList1.size(); i++) {
            Tag mTag = (Tag) mTagList1.get(i);
            if (mTag.getText().equals(chip.getText())) {
                int type = mTag.getType();
                Tag t;
                if (type == 1) {
                    t = new Tag(mTag.getText(), 2);
                } else {
                    t = new Tag(mTag.getText(), 1);
                }

                mTagList1.remove(mTag);
                mTagList1.add(i, t);
                mTextChipLayout.setChipList(mTagList1);
                mTextChipLayout.setOnChipClickListener(this);

            }
        }

    }
}