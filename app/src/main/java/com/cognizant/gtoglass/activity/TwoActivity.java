package com.cognizant.gtoglass.activity;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by devarajns on 10/01/14.
 */
public class TwoActivity extends TargetFinderActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");
        mTargetListIndex = 1;

        super.onCreate(savedInstanceState);
    }
}
