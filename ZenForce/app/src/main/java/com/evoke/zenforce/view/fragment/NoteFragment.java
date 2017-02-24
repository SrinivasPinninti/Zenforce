package com.evoke.zenforce.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evoke.zenforce.R;


public class NoteFragment extends Fragment {

    private String mNotes;



    public static NoteFragment getInstance(String notes) {
        NoteFragment f = new NoteFragment();
        Bundle args = new Bundle();
        args.putString("notes", notes);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotes = getArguments().getString("notes");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_notes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvNotes = (TextView) view.findViewById(R.id.tvNotes);
        if (mNotes != null && !TextUtils.isEmpty(mNotes)) {

            tvNotes.setText(mNotes);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}