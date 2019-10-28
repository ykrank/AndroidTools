package com.github.ykrank.androidtools_demo.binding;

import androidx.databinding.BindingAdapter;

import android.widget.TextView;
import android.widget.Toast;

import com.github.ykrank.androidtools.extension.ContextExtensionKt;


public final class ViewBindingAdapter {

    private ViewBindingAdapter() {
    }

    @BindingAdapter("clickToast1")
    public static void clickToast(TextView view, String msg) {
        view.setOnClickListener(v -> {
            ContextExtensionKt.toast(v.getContext(), msg, Toast.LENGTH_SHORT);
        });
    }
}
