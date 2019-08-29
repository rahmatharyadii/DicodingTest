package com.rahmatharyadi.dicodingtest.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.rahmatharyadi.dicodingtest.R;

public class LoadingClass {

    public static ProgressDialog loadingAnimation(Context context) {
        ProgressDialog loading = new ProgressDialog(context, R.style.SimpleLoadingStyle);
        loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        return loading;
    }
}
