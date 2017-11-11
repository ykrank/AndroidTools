package com.github.ykrank.androidtools.binding;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.text.format.DateUtils;
import android.widget.TextView;

public final class LibTextViewBindingAdapter {
    private static int defaultTextColor;

    private LibTextViewBindingAdapter() {
    }

    @BindingAdapter("underlineText")
    public static void setUnderlineText(TextView textView, String text) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
        textView.setText(text);
    }

    @BindingAdapter("relativeDateTime")
    public static void setRelativeDateTime(TextView textView, long datetime) {
        textView.setText(DateUtils.getRelativeDateTimeString(textView.getContext(), datetime,
                DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0));
    }
}
