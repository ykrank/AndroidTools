package com.github.ykrank.androidtools.binding;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class LibTextViewBindingAdapter {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

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

    @BindingAdapter("second")
    public static void setSecondTime(TextView textView, long datetimeSecond) {
        textView.setText(df.format(new Date(datetimeSecond*1000)));
    }
}
