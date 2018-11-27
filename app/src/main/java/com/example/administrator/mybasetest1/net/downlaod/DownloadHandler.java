package com.example.administrator.mybasetest1.net.downlaod;

import android.os.AsyncTask;

import com.example.administrator.mybasetest1.R;
import com.example.administrator.mybasetest1.net.RestCreator;
import com.example.administrator.mybasetest1.net.callback.IError;
import com.example.administrator.mybasetest1.net.callback.IFailure;
import com.example.administrator.mybasetest1.net.callback.IRequest;
import com.example.administrator.mybasetest1.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/11/13.
 */

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS= RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url, IRequest request, String downloadDir, String extension, String name, ISuccess success, IFailure failure, IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    public final void handleDownload(){
        if (REQUEST!=null) {
            REQUEST.onRequestStart();
        }
        RestCreator.getRestService()
                .downLoad(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task=new SaveFileTask(REQUEST,SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,responseBody,NAME);
                            if (task.isCancelled()) {
                                if (REQUEST!=null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if (ERROR!=null) {
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE!=null) {
                            FAILURE.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });

    }
}
















