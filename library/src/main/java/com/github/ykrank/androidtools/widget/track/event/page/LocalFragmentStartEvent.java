package com.github.ykrank.androidtools.widget.track.event.page;

import android.app.Fragment;

import com.github.ykrank.androidtools.widget.track.event.TrackEvent;

public class LocalFragmentStartEvent extends TrackEvent {
    private Fragment fragment;

    public LocalFragmentStartEvent(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
