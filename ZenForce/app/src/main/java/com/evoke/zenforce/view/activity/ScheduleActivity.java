package com.evoke.zenforce.view.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.pojo.NavigationItem;

public class ScheduleActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initUI();

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Bundle bundle = getIntent().getExtras();
        NavigationItem item = bundle.getParcelable("NAVIGATION_ITEM");
        getSupportActionBar().setTitle(item.getName());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(item.getColor()));
    }
}
