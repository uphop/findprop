package com.lightson.findpropapp.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;

public class LogHelper {

    private final int DEFAULT_LOG_LEVEL = Log.INFO;
    private String tag;
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("MissingPermission")
    public LogHelper(String tag, Context context) {
        this.tag = tag;
        this.context = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.context);
    }

    public void logEvent(UsageEventEnum eventName) {
        logEvent(eventName, null, tag, null, DEFAULT_LOG_LEVEL);
    }

    public void logEvent(UsageEventEnum eventName, Map<String, String> eventParams) {
        logEvent(eventName, eventParams, tag, null, DEFAULT_LOG_LEVEL);
    }

    public void logEvent(UsageEventEnum eventName, Map<String, String> eventParams, String eventTag, String eventMessage, int eventLevel) {
        if (eventName != null) {
            Bundle eventBundle = new Bundle();
            if (eventParams != null && eventParams.size() > 0) {
                for (String key : eventParams.keySet()) {
                    eventBundle.putString(key, eventParams.get(key));
                }
            }
            mFirebaseAnalytics.logEvent(eventName.toString(), eventBundle);

            StringBuilder eventText = new StringBuilder();
            eventText.append(eventName);
            if (eventMessage != null) {
                eventText.append(", ");
                eventText.append(eventMessage);
            }
            if (eventParams != null) {
                eventText.append(", [");
                eventText.append(eventParams.toString());
                eventText.append("]");
            }
            Log.println(eventLevel, eventTag, eventText.toString());
        }
    }

    public void showMessage(int stringId) {
        Toast toast = Toast.makeText(context,
                context.getResources().getString(stringId),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
