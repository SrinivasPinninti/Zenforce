package com.evoke.zenforce.view.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.database.dao.VisitDAO;
import com.evoke.zenforce.utility.Util;
import com.evoke.zenforce.view.activity.LaunchActivity;
import com.evoke.zenforce.view.activity.NoteActivity;
import com.evoke.zenforce.view.activity.PhotoActivity;
import com.evoke.zenforce.view.activity.ScanActivity;
import com.evoke.zenforce.view.activity.VisitActivity;
import com.evoke.zenforce.view.service.TimerService;

import java.lang.ref.WeakReference;

public class ActivitiesFragment extends Fragment implements View.OnClickListener {

    public static final String TAG ="ActivitiesFragment";




    private VisitActivity mActivity;
    private Context mContext;

    private RelativeLayout rlTimer;
    private RelativeLayout rlPhoto;
    private RelativeLayout rlNote;

    private TextView  tvTimer;
    private TextView  tvTimerTitle;


    private long  mVisitId;

    private long    mPlaceId;
    private String  mPlaceName;
    private String  mPlaceAddress;


    private ImageView imgUpload;
    private ImageView imgNoteUpload;

    //-----------------------------------------------//

    private TimerService timerService;
    private boolean serviceBound;


    // Handler to update the UI every second when the timer is running
    private final Handler mUpdateTimeHandler = new UIUpdateHandler(this);

    // Message type for the handler
    private final static int MSG_UPDATE_TIME = 0;
    private Button btnForm;

    //---------------------------------------------//



    public ActivitiesFragment() {
        // Required empty public constructor
    }

    public static ActivitiesFragment newInstance() {
        ActivitiesFragment fragment = new ActivitiesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate....");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView....");
        VisitActivity activity = (VisitActivity) getActivity();
        mPlaceId = activity.getPlaceId();
        mPlaceName = activity.getPlaceName();
        mPlaceAddress = activity.getPlaceAddress();
//        mLocationId = activity.getPlaceId();
        View v = inflater.inflate(R.layout.fragment_activities, container, false);
        initUI(v);

        return v;
    }


    private void initUI(View v) {

        btnForm = (Button) v.findViewById(R.id.btnForm);
        btnForm.setOnClickListener(this);
        rlTimer = (RelativeLayout) v.findViewById(R.id.rlTimer);
        rlTimer.setOnClickListener(this);
        rlPhoto = (RelativeLayout) v.findViewById(R.id.rlPhoto);
        rlPhoto.setOnClickListener(this);
        imgUpload = (ImageView) v.findViewById(R.id.imgUpload);
        imgNoteUpload = (ImageView) v.findViewById(R.id.imgNoteUpload);
        imgUpload.setVisibility(View.INVISIBLE);
        rlNote = (RelativeLayout) v.findViewById(R.id.rlNote);
        rlNote.setOnClickListener(this);
        tvTimer = (TextView) v.findViewById(R.id.tvTimer);
        tvTimerTitle = (TextView) v.findViewById(R.id.tvTimerTitle);
//        imgStartTimer = (ImageView) v.findViewById(R.id.imgStartTimer);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (VisitActivity)getActivity();
        Log.v(TAG, "onAttach....");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause....");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume....");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting and binding service");
        }
        Intent i = new Intent(mActivity, TimerService.class);
        mActivity.startService(i);
        mActivity.bindService(i, mConnection, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop....");
        Log.e(TAG, "serviceBound : " + serviceBound);
        Log.e(TAG, "isTimerRunning : " + timerService.isTimerRunning());
        updateUIStopRun();
        if (serviceBound) {
            // If a timer is active, foreground the service, otherwise kill the service
            if (timerService.isTimerRunning()) {
                Log.e(TAG, "start foreground....");
                timerService.foreground();
            } else {
                Log.e(TAG, "stop Service....");
                mActivity.stopService(new Intent(mActivity, TimerService.class));
            }
            // Unbind the service
            mActivity.unbindService(mConnection);
            serviceBound = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach....");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy....");
    }



    public void runButtonClick() {

        if (serviceBound && !timerService.isTimerRunning()) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Starting timer");
            }

            startTimer();
//            timerService.startTimer();
//            updateUIStartRun();
        } else if (serviceBound && timerService.isTimerRunning()) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Stopping timer");
            }
            stopTimer();
//            timerService.stopTimer();
//            updateUIStopRun();
        }
    }

    /**
     * Updates the UI when a run starts
     */
    private void updateUIStartRun() {
        mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
//        timerButton.setText("STOP");
//        showTimerDialog(STOP_TIMER);
        tvTimerTitle.setText("STOP TIMER");

    }

    /**
     * Updates the UI when a run stops
     */
    private void updateUIStopRun() {
        mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
//        timerButton.setText("START");
//        showTimerDialog(START_TIMER);
        tvTimerTitle.setText("START TIMER");

    }

    /**
     * Updates the timer readout in the UI; the service must be bound
     */
    private void updateUITimer() {
        if (serviceBound) {
            tvTimer.setText(timerService.formatElapsedTime());
        }
    }


    private void stopTimer() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mPlaceName);
        builder.setMessage("Do you want to stop timer ?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                timerService.stopTimer();
                updateUIStopRun();
                endVisit();

                Intent intent = new Intent(mActivity, LaunchActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


    private void startTimer() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mPlaceName);
        builder.setMessage("Do you want to start timer ?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                timerService.startTimer();
                updateUIStartRun();
                startVisit();

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * Callback for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service bound");
            }
            TimerService.RunServiceBinder binder = (TimerService.RunServiceBinder) service;
            timerService = binder.getService();
            serviceBound = true;
            // Ensure the service is not in the foreground when bound
            timerService.background();
            // Update the UI if the service is already running the timer
            if (timerService.isTimerRunning()) {
                updateUIStartRun();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service disconnect");
            }
            serviceBound = false;
        }
    };



    /**
     * When the timer is running, use this handler to update
     * the UI every second to show timer progress
     */
    static class UIUpdateHandler extends Handler {

        private final static int UPDATE_RATE_MS = 1000;
        private final WeakReference<ActivitiesFragment> activity;

        UIUpdateHandler(ActivitiesFragment activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            if (MSG_UPDATE_TIME == message.what) {
                if (Log.isLoggable(TAG, Log.VERBOSE)) {
                    Log.v(TAG, "updating time");
                }
                activity.get().updateUITimer();
                sendEmptyMessageDelayed(MSG_UPDATE_TIME, UPDATE_RATE_MS);
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, " onSaveInstanceState..." + outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated savedInstanceState : " + savedInstanceState);
        if (savedInstanceState != null) {
//            mVisitBean = savedInstanceState.getParcelable("mVisit");
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlTimer:
                runButtonClick();
               /* Log.v(TAG, " m_stopwatchService is running  : " + m_stopwatchService.isStopwatchRunning());
                if ( m_stopwatchService != null ) {
                    if ( m_stopwatchService.isStopwatchRunning() ) {
                        showTimerDialog(STOP_TIMER);   // Already timer is running, Stop it
                    } else {
                        showTimerDialog(START_TIMER);  // Timer not yet started, Start it
                    }
                }*/
                break;
            case R.id.rlPhoto:

                if (!timerService.isTimerRunning()) {  // TODO : ask to start timer to start visit
                    Toast.makeText(mActivity, "Start Timer to start visit!!", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent photoIntent = new Intent(mActivity, PhotoActivity.class);
                photoIntent.putExtra("visitId", mVisitId);
                photoIntent.putExtra("visitName", mPlaceName);
                Log.d(TAG, " startActivityForResult PhotoActivity.....");
                startActivityForResult(photoIntent, 500);

                break;
            case R.id.rlNote:

                if (!timerService.isTimerRunning()) {  // TODO : ask to start timer to start visit
                    Toast.makeText(mActivity, "Start Timer to start visit!!", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent noteIntent = new Intent(mActivity, NoteActivity.class);
                noteIntent.putExtra("visitId", mVisitId);
                noteIntent.putExtra("visitName", mPlaceName);
                noteIntent.putExtra("mPlaceId", mPlaceId);
//                noteIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Log.d(TAG, " startActivityForResult NoteActivity.....");
                startActivityForResult(noteIntent, 600);
                break;

            case R.id.btnForm :

                if (!timerService.isTimerRunning()) {  // TODO : ask to start timer to start visit
                    Toast.makeText(mActivity, "Start Timer to start visit!!", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(mActivity, ScanActivity.class);
                intent.putExtra("visitId", mVisitId);
                startActivityForResult(intent, 700);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, " onActivityResult requestCode : " + requestCode);

        switch (requestCode) {
            case 500:
                if (resultCode == getActivity().RESULT_OK) {
                    Log.d(TAG, " PHOTO RESULT_OK ");
                    imgUpload.setVisibility(View.VISIBLE);
                }
                break;
            case 600:
                Log.d(TAG, " NOTE RESULT_OK ");
                imgNoteUpload.setVisibility(View.VISIBLE);
                break;
            case 700:
                Log.d(TAG, " REPORT FORM RESULT_OK ");
                break;
        }

    }


    private void startVisit() {


        long startTime = System.currentTimeMillis();
        Log.d(TAG, " set startTime : " + startTime);


        VisitDAO visitDao= VisitDAO.getSingletonInstance(mActivity);
        VisitBean bean = new VisitBean();

        bean.setPlaceId(mPlaceId);
        bean.setName(mPlaceName);
        bean.setAddress(mPlaceAddress);
        bean.setStart_time(startTime);
        bean.setTimeStamp(System.currentTimeMillis());

//        Util.sVisitId = visitDao.insert(bean);
        mVisitId = visitDao.insert(bean);
        Log.d(TAG, " mVisitId : " + mVisitId);
    }


    private void endVisit() {

//        Log.d(TAG, "endVisit  Util.sVisitId : " + Util.sVisitId);
        Log.d(TAG, "endVisit  mVisitId : " + mVisitId);

        if (mVisitId == 0) return;


        long endTime = System.currentTimeMillis();
        Log.d(TAG, " set endTime : " + endTime);


        String selection = DbConstants.VisitTable.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(mVisitId) };

        VisitDAO visitDao = VisitDAO.getSingletonInstance(mActivity);
        VisitBean bean = visitDao.getLastInsertedRow(null, selection, selectionArgs, null);

        Log.d(TAG, " prev bean : " + bean);
        bean.setEnd_time(endTime);


        String selection1 = DbConstants.VisitTable.COLUMN_ID + " = ?";
        String[] selectionArgs1 = new String[] { String.valueOf(mVisitId) };
        long updateId = visitDao.update(bean, selection1, selectionArgs1);

        Log.d(TAG, " updateId : " + updateId);

        // Reset visitId
       Util.sVisitId = 0;
       Util.sPhoto_count = 0;
       Util.sNote_count = 0;

    }



    private void finishVisitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mPlaceName);
        builder.setMessage("Are you sure you want to finish the visit ?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                timerService.stopTimer();
                updateUIStopRun();
                endVisit();


                Intent intent = new Intent(mActivity, LaunchActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.create().show();
    }

    public void onBackPressed() {
        Log.e(TAG, " Back key pressed...");
        finishVisitDialog();
    }





}
