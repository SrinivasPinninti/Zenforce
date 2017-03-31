package com.test.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnStart;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

       switch (v.getId()) {

           case R.id.btnStart:

               Intent startIntent = new Intent(this, MyService.class);
               startService(startIntent);

               break;
           case R.id.btnStop:

               Intent stopIntent = new Intent(this, MyService.class);
               stopService(stopIntent);

               break;
       }

    }
}
