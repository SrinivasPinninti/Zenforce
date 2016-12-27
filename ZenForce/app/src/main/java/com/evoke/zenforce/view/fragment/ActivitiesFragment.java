package com.evoke.zenforce.view.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.TimerEntityBean;
import com.evoke.zenforce.model.database.dao.TimerDAO;
import com.evoke.zenforce.view.activity.NoteActivity;
import com.evoke.zenforce.view.activity.PhotoActivity;
import com.evoke.zenforce.view.activity.VisitActivity;
import com.evoke.zenforce.view.application.ZenForceApplication;
import com.evoke.zenforce.view.service.StopwatchService;

public class ActivitiesFragment extends Fragment implements View.OnClickListener, PhotoActivity.PhotoInsertedCallBack {

    public static final String TAG ="ActivitiesFragment";


    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 111;
    private static final int UPLOAD_IMAGE_REQUEST_CODE = 222;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private static final int START_TIMER = 100;
    private static final int STOP_TIMER = 200;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Zenforce Images";
    private static final int PHOTO_UPLOAD = 1;
    private static final int LOADER_ID = 20;


    private VisitActivity mActivity;
    private Context mContext;

    private RelativeLayout rlTimer;
    private RelativeLayout rlPhoto;
    private RelativeLayout rlNote;

    private TextView  tvTimer;
    private TextView  tvTimerTitle;
    private TextView tvPlaceName;
    private TextView tvPlaceAddress;

    private String mVisitName;
//    private String mLocationId;


    private ImageView imgUpload;
    private ImageView imgNoteUpload;



    private final long mFrequency = 1000;    // milliseconds
    private final int TICK_WHAT = 2;

    private long mTimerInsertedId;
    private long mVisitId;




    private Handler mHandler = new Handler() {
        public void handleMessage(Message m) {
            updateElapsedTime();
            sendMessageDelayed(Message.obtain(this, TICK_WHAT), mFrequency);
        }
    };

    // Connection to the back ground StopwatchService
    private StopwatchService m_stopwatchService;
    private ServiceConnection m_stopwatchServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            m_stopwatchService = ((StopwatchService.LocalBinder)service).getService();
//            showCorrectButtons();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            m_stopwatchService = null;
        }
    };



    private void bindStopwatchService() {
        mContext.bindService(new Intent(mContext, StopwatchService.class),
                m_stopwatchServiceConn, Context.BIND_AUTO_CREATE);
    }

    private void unbindStopwatchService() {
        if ( m_stopwatchService != null ) {
            mContext.unbindService(m_stopwatchServiceConn);
        }
    }



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

        VisitActivity activity = (VisitActivity) getActivity();
        mVisitId = activity.getVisitId();
        mVisitName = activity.getVisitName();
//        mLocationId = activity.getLocationId();
        Log.v(TAG, "onCreateView mVisitId : "+mVisitId);
        View v = inflater.inflate(R.layout.fragment_activities, container, false);
        initUI(v);

        mContext.startService(new Intent(mContext, StopwatchService.class));
        bindStopwatchService();
        mHandler.sendMessageDelayed(Message.obtain(mHandler, TICK_WHAT), mFrequency);

        return v;
    }


    private void initUI(View v) {
        tvPlaceName = (TextView) v.findViewById(R.id.tvPlaceName);
        tvPlaceAddress = (TextView) v.findViewById(R.id.tvPlaceAddress);
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
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop....");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach....");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindStopwatchService();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.rlTimer:
                Log.v(TAG, " m_stopwatchService is running  : " + m_stopwatchService.isStopwatchRunning());
                if ( m_stopwatchService != null ) {
                    if ( m_stopwatchService.isStopwatchRunning() ) {
                        showTimerDialog(STOP_TIMER);   // Already timer is running, Stop it
                    } else {
                        showTimerDialog(START_TIMER);  // Timer not yet started, Start it
                    }
                }
                break;
            case R.id.rlPhoto:
                ZenForceApplication.getInstance().setListener(this);
                Intent photoIntent = new Intent(mActivity, PhotoActivity.class);
                photoIntent.putExtra("visitName", mVisitName);
                photoIntent.putExtra("visit_id", mVisitId);
                startActivity(photoIntent);

                break;
            case R.id.rlNote:
                Intent noteIntent = new Intent(mActivity, NoteActivity.class);
                noteIntent.putExtra("visitName", mVisitName);
                noteIntent.putExtra("mVisitId", mVisitId);
                noteIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(noteIntent);
                break;
        }
    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.v(TAG, " onActivityResult from photoActivity......");
        if (requestCode == 500) {
            if (resultCode == getActivity().RESULT_OK) {
                Log.v(TAG, " got photo details......");

                long photoRowId = intent.getLongExtra("photoRowId", 0);
                Log.v(TAG, " photoRowId " + photoRowId);
                Log.v(TAG, " mVisitId " + mVisitId);

                updateVisitTable(photoRowId);


            }
        }
    }*/



   /* private void updateVisitTable(long rowId) { // TODO : update TIMER, PHOTO, NOTE ID's

        Log.v(TAG, "updateVisitTable  mVisitId : " + mVisitId + " & rowId : " + rowId);

        VisitBean bean = new VisitBean();
//        bean.setPhotoId(rowId);
        String selection = DbConstants.VisitTable.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[] {String.valueOf(mVisitId)};

        VisitDAO dao = VisitDAO.getSingletonInstance(mContext);
//        dao.update(bean, selection, selectionArgs);
    }*/

    private void showTimerDialog(final int cmd) {

        String msg = (cmd == START_TIMER) ? "Start timer ?" : "Stop timer ?";

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mVisitName);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (cmd == START_TIMER) {
                    Log.d(TAG, "start button clicked");
                    tvTimerTitle.setText("END VISIT");
                    m_stopwatchService.start();
                    insertStartTime();
                } else {
                    Log.d(TAG, "pause button clicked");
                    tvTimerTitle.setText("START VISIT");
                    m_stopwatchService.pause();
                    m_stopwatchService.reset();
                    updateEndTime();
                    mActivity.finish();
                }

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


    public void updateElapsedTime() {
        if ( m_stopwatchService != null )
            tvTimer.setText(m_stopwatchService.getFormattedElapsedTime());
    }



    private void insertStartTime() {
        TimerDAO dao = TimerDAO.getSingletonInstance(mContext);
        TimerEntityBean bean = new TimerEntityBean();
        long startTime = System.currentTimeMillis()/1000;
        Log.d(TAG, " startTime : " + startTime);
        bean.setStartTime(startTime);
        bean.setEndTime(0);
        mTimerInsertedId = dao.insert(bean);
        Log.d(TAG, " mTimerInsertedId : " + mTimerInsertedId);
    }


    private void updateEndTime() {

        Log.d(TAG, " mTimerInsertedId : " + mTimerInsertedId);

        long endTime = System.currentTimeMillis()/1000;

        TimerDAO dao = TimerDAO.getSingletonInstance(mContext);
//        String[] projection = new String[] { DbConstants.TimerTable.COLUMN_START_TIME };
        String selection = DbConstants.TimerTable.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[] {String.valueOf(mTimerInsertedId)};
        long startTime = dao.getStartTime(null, selection, selectionArgs, null);
        Log.d(TAG, " startTime : " + startTime);
        Log.d(TAG, " endTime : " + endTime);
        Log.d(TAG, " diff : " + (startTime - endTime));
        long duration = Math.abs(startTime - endTime);
        Log.d(TAG, " duration : " + duration);
        TimerEntityBean bean = new TimerEntityBean();
        bean.setVisitId(mVisitId);
        bean.setEndTime(endTime);
        bean.setDuration(duration);
        long timerId = dao.updateEndTime(bean, selection, selectionArgs);
        Log.d(TAG, " timerId : " + timerId);
//        updateVisitTable(timerId);
    }


    @Override
    public void photoInserted(long rowId) {
        Log.d(TAG, " photoInserted : " + rowId);
//        updateVisitTable(rowId);
    }
}
