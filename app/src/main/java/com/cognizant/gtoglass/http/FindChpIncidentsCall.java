package com.cognizant.gtoglass.http;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

public class FindChpIncidentsCall extends AsyncCall {

    public static interface OnFindChpIncidentsListener {
        void onFindChpIncidents(List<Placemark> data);
    }

    public static final float M_PER_MI = 1609.344f;

    private static final String LOG_TAG = "FindChpIncidentsCall";

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (null == msg.obj) {
                DialogUtil.safeDismiss(mProgressDialog);
                showErrorDialog("Couldn't connect.");
                return;
            }

            List<Placemark> data = (List<Placemark>) msg.obj;

            for (Placemark mark : data) {
                mark.setDistanceFromDestination(mDestination.getDistanceInMiles(mark.getLat(), mark.getLon()));
            }

            DialogUtil.safeDismiss(mProgressDialog);
            mReceiver.onFindChpIncidents(data);

        }
    };

    private final OnFindChpIncidentsListener mReceiver;

    private final FindRequestData mDestination;

    public FindChpIncidentsCall(final Activity activity,
                                final OnFindChpIncidentsListener receiver, final FindRequestData destination) {


        super(activity);
        Log.i(LOG_TAG, "FindChpIncidentsCall");
        mReceiver = receiver;
        mDestination = destination;
    }

    public void downloadIncidents() {
        Log.i(LOG_TAG, "downloadIncidents");

        showProgressDialog("Loading CHP incidents...", false, null);

        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = buildUrl();

                List<Placemark> data = KmlResultParser.getPlacemarks(url);

                Message message = mHandler.obtainMessage(0, data);
                mHandler.sendMessage(message);
            }
        };
        thread.start();
    }

    private String buildUrl() {

        String url = "http://quickmap.dot.ca.gov/data/chp.kml";
        return url;

    }

}
