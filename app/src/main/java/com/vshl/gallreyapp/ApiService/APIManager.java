package com.vshl.gallreyapp.ApiService;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vshl.gallreyapp.ApiService.models.ImagesModel;
import com.vshl.gallreyapp.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {


    private Context context;
    private static APIManager mApiManager;
    private ProgressDialog dialog;
    private API mApiService;



    private APIManager() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        mApiService = retrofit.create(API.class);
    }

    public static APIManager getInstance() {
        if (mApiManager == null) {
            mApiManager = new APIManager();
        }
        return mApiManager;
    }

    private OkHttpClient getClient() {
        return new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Content-Type",
                        Credentials.basic("application", "json"));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
    }

    


    private MediaType getMediaTypePlainText() {
        return MediaType.parse("text/plain");
    }

    



    public void getcity(Callback<ArrayList<ImagesModel>> aCallBack){
        Call<ArrayList<ImagesModel>> call = mApiService.getList();
        call.enqueue(aCallBack);

    }


    public synchronized void showProgressDialog(Context mContext, boolean showFullScreen, @Nullable String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(mContext, R.style.MyAlertDialogStyle);
            dialog.setCancelable(false);
        }
        try {
            if (message != null && !message.isEmpty()) {
                dialog.setMessage(message);
            } else {
                dialog.setMessage("");
            }
            if (!dialog.isShowing()) {
                dialog.show();
                if (showFullScreen) {
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dialog_progress);
                    }
                }
            }
        } catch (WindowManager.BadTokenException e) {
            Log.e("TAG", "" + e.getMessage());
        }
    }

    public synchronized void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public synchronized void showAlertDialog(Context aContext, boolean setNegativeButton,
                                             String message, String positiveText, String negativeText,
                                             @NonNull DialogInterface.OnClickListener positiveButtonListener,
                                             @Nullable DialogInterface.OnClickListener negativeButtonListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(aContext);
        builder.setTitle(aContext.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, positiveButtonListener);
        if (setNegativeButton) {
            builder.setNegativeButton(negativeText, negativeButtonListener);
        }
        builder.show();


    }

}
