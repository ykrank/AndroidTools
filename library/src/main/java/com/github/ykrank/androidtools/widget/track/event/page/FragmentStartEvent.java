package com.github.ykrank.androidtools.widget.track.event.page;

import android.support.v4.app.Fragment;

import com.github.ykrank.androidtools.widget.track.event.TrackEvent;

public class FragmentStartEvent extends TrackEvent {
    private Fragment fragment;

    public FragmentStartEvent(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
