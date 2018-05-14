package com.github.ykrank.androidtools.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.AnyRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.github.ykrank.androidtools.GlobalData;
import com.github.ykrank.androidtools.R;

public final class ResourceUtil {

    private ResourceUtil() {
    }

    /**
     * Retrieves the resource id in the Theme.
     *
     * @param theme The theme we want to retrieve attribute.
     * @param resId The resource id of of the desired theme attribute.
     * @return The corresponding resource id.
     */
    @AnyRes
    public static int getResourceId(Resources.Theme theme, @AttrRes int resId) {
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(resId, typedValue, true);
        return typedValue.resourceId;
    }

    @ColorInt
    public static int getAttrColorInt(Context context, @AttrRes int resId) {
        int colorRes = getResourceId(context.getTheme(), resId);
        return ContextCompat.getColor(context, colorRes);
    }

    /**
     * Sets the scaling factor for fonts displayed on the display.
     * After api 25, updateConfiguration not valid
     * @param scale the scaling factor.
     */
    @SuppressLint("RestrictedApi")
    public static Context setScaledDensity(Context context, float scale) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Resources sysResources = Resources.getSystem();

        Configuration config = resources.getConfiguration();
        float sysFontScale = sysResources.getConfiguration().fontScale;
//        if (config.fontScale != sysFontScale * scale) {
            config.fontScale = sysFontScale * scale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return context.createConfigurationContext(config);
            } else {
                resources.updateConfiguration(config, displayMetrics);
            }
//        }
        return context;
    }

    /**
     * Whether current layout direction is from Right to Left.
     */
    public static boolean isRTL(Resources resources) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && resources.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    public static
    @Nullable
    String getAppMeta(Context context, String name) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return ai.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            L.e("getAppMeta", e);
        }
        return null;
    }

    public static ColorStateList getTextColorPrimary(Context mContext) {
        return ContextCompat.getColorStateList(mContext,
                ResourceUtil.getResourceId(mContext.getTheme(), android.R.attr.textColorPrimary));
    }

    public static int getNavigationBarHeight(Context context){
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
