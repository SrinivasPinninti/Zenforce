package com.evoke.zenforce.view.custom.builders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.view.custom.ChipBuilder;

/**
 * Created by spinninti on 11/24/2016.
 */
public class SimpleChip extends ChipBuilder {

    @Override
    public View getChip(LayoutInflater inflater, String data) {
        View view = inflater.inflate(R.layout.view_chip_simple, null);
        TextView txtChip = (TextView) view.findViewById(R.id.txt_chip);
        txtChip.setText(data);
        return view;
    }
}