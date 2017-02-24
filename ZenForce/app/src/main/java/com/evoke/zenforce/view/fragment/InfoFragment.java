package com.evoke.zenforce.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.view.activity.VisitActivity;
import com.squareup.picasso.Picasso;

public class InfoFragment extends Fragment  implements View.OnClickListener{


    private static final String TAG = "InfoFragment";
    private TextView    mTvPhoneNumber;
    private TextView    mTvWebsiteUrl;
    private ImageView   mMapView;
    private ImageView   mImgCall;

    private Context     mContext;

    private long mVisitId;

    private String mPhone;
    private String mUrl;
    private String mLat;
    private String mLng;


    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        VisitActivity activity = (VisitActivity) getActivity();
        mVisitId = activity.getPlaceId();
        mPhone   = activity.getPhone();
        mUrl     = activity.getUrl();
        mLat     = activity.getLat();
        mLng     = activity.getLng();

        Log.d(TAG, "" + mPhone);
        Log.d(TAG, "" + mUrl);
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        mTvPhoneNumber  = (TextView) v.findViewById(R.id.tvPhoneNumber);
        mImgCall        = (ImageView) v.findViewById(R.id.imgCall);
        mTvWebsiteUrl   = (TextView) v.findViewById(R.id.tvWebsiteUrl);
        mTvWebsiteUrl.setClickable(true);
        mTvWebsiteUrl.setMovementMethod(LinkMovementMethod.getInstance());
        mTvWebsiteUrl.setOnClickListener(this);

        mMapView        = (ImageView) v.findViewById(R.id.mapView);
        setUpData();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setUpData() {
        Log.e(TAG, " phone & website..." + mPhone + " & " + mUrl);
        if (mPhone != null) {
            mImgCall.setVisibility(View.VISIBLE);
            mImgCall.setOnClickListener(this);
        } else {
            mImgCall.setVisibility(View.INVISIBLE);
        }

        if (mUrl != null) {
                mTvPhoneNumber.setText(mPhone);
                String text = "<a href=" + mUrl + "/>";
                mTvWebsiteUrl.setText(Html.fromHtml(mUrl));
        }

        Log.d(TAG, " lat : " + mLat);
        Log.d(TAG, " lng : " + mLng);
        if (mLat != null && mLng != null) {
            String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + mLat + "," + mLng + "&zoom=13&size=600x400&maptype=roadmap&markers=color:green%7Clabel:S%7C" + mLat + "," + mLng + "&key=AIzaSyB2WXedVGvVwOvylJvTqhHfTqz-OTHtG-M";
            Picasso.with(getActivity()).load(url).into(mMapView);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgCall:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPhone));
                startActivity(intent);
                break;

            case R.id.tvWebsiteUrl:

                break;
        }

    }




}
