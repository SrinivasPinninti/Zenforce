package com.evoke.zenforce.view.custom;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by spinninti on 11/24/2016.
 */
public abstract class ChipBuilder {

    private int position;

    public int getViewTypeCount() {
        return 0;
    }

    public final int getPosition() {
        return position;
    }

    public final void setPosition(int position) {
        this.position = position;
    }

    public abstract View getChip(LayoutInflater inflater, String data);
}
