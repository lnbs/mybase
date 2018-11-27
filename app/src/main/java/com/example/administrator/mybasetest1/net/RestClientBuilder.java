package com.example.administrator.mybasetest1.net;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.administrator.mybasetest1.net.callback.IError;
import com.example.administrator.mybasetest1.net.callback.IFailure;
import com.example.administrator.mybasetest1.net.callback.IRequest;
import com.example.administrator.mybasetest1.net.callback.ISuccess;
import com.example.administrator.mybasetest1.net.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/11/14.
 */

public final class RestClientBuilder {
    private static final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private String mURL=null;
    private IRequest mIRequest=null;
    private ISuccess mISuccess=null;
    private IFailure mIFailure=null;
    private IError mIError=null;
    private RequestBody mBody=null;
    private Context mContext=null;
    private LoaderStyle mLoadStyle=null;
    private File mFile=null;
    private String mDownloadDir=null;
    private String mExtension=null;
    private String mName=null;

    public RestClientBuilder() {
    }

    public final RestClientBuilder url(String url){
        this.mURL=url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key,Object value){
        PARAMS.put(key,value);
        return this;
    }

    public final RestClientBuilder file(File file){
        this.mFile=file;
        return this;
    }

    public final RestClientBuilder file(String filePath){
        this.mFile=new File(filePath);
        return this;
    }

    public final RestClientBuilder name(String name){
        this.mName=name;
        return this;
    }

    public final RestClientBuilder dir(String dir){
        this.mDownloadDir=dir;
        return this;
    }

    public final RestClientBuilder extension(String extension){
        this.mExtension=extension;
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody= RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest){
        this.mIRequest=iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        this.mISuccess=iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure=iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mIError=iError;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle){
        this.mContext=context;
        this.mLoadStyle=loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext=context;
        this.mLoadStyle=LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }
//    String url, Map<String, Object> params, IRequest request, String downloadDir, String extension, String name, ISuccess success, IFailure failure, IError error, RequestBody body
//            ,  File file,LoaderStyle loaderStyle, Context context
    public final RestClient build(){
        return new RestClient(mURL,PARAMS,mIRequest,mDownloadDir,mExtension,mName,mISuccess,mIFailure,mIError,mBody,mFile,mLoadStyle,mContext);
    }

}


















