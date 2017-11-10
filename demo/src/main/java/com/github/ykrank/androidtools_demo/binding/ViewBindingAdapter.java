package com.github.ykrank.androidtools_demo.binding;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ykrank.androidtools.extension.ContextExtensionKt;
import com.github.ykrank.androidtools.util.ColorDrawableUtils;


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
