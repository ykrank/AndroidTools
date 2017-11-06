package com.github.ykrank.androidtools.widget.track;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Map;

import com.github.ykrank.androidtools.data.TrackUser;

/**
 * Created by ykrank on 2016/12/29.
 */

public interface TrackAgent {
    void init(Context context);

    void setUser(@NonNull TrackUser user);

    void onResume(@NonNull Activity activity);

    void onPause(@NonNull Activity activity);

    void onPageStart(@NonNull Context context, String string);

    void onPageEnd(@NonNull Context context, String string);

    void onEvent(@NonNull Context context, String name, String label, Map<String, String> data);
}
