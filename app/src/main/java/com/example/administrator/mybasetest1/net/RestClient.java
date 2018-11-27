package com.example.administrator.mybasetest1.net;

import android.content.Context;

import com.example.administrator.mybasetest1.net.callback.IError;
import com.example.administrator.mybasetest1.net.callback.IFailure;
import com.example.administrator.mybasetest1.net.callback.IRequest;
import com.example.administrator.mybasetest1.net.callback.ISuccess;
import com.example.administrator.mybasetest1.net.downlaod.DownloadHandler;
import com.example.administrator.mybasetest1.net.loader.AppLoader;
import com.example.administrator.mybasetest1.net.loader.LoaderStyle;
import com.example.administrator.mybasetest1.utils.log.LatteLogger;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2018/11/13.
 */

public final class RestClient {

    private static final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    /**-------------------核心请求的方法--------------------------------*/
    public RestClient(String url, Map<String, Object> params, IRequest request, String downloadDir, String extension, String name, ISuccess success, IFailure failure, IError error, RequestBody body
            ,  File file,LoaderStyle loaderStyle, Context context) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.FILE=file;
        this.CONTEXT = context;
        PARAMS.putAll(params);
    }

    private <T> Disposable request(final HttpMethod method){
        final RestService restService = RestCreator.getRestService();
        Observable<String> stringObservable = null;
        if (REQUEST!=null) {
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE!=null) {
            AppLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method){
            case GET:
                stringObservable = restService.get(URL, PARAMS);
                break;
            case POST:
                stringObservable = restService.post(URL, PARAMS);
                break;
            case POST_RAW:
                stringObservable = restService.postRaw(URL, BODY);
                break;
            case PUT:
                stringObservable = restService.put(URL, PARAMS);
                break;
            case PUT_RAW:
                stringObservable = restService.putRaw(URL, BODY);
                break;
            case DELETE:
                stringObservable = restService.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody=
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body=MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                stringObservable = restService.upload(URL, body);
                break;
        }
        return stringObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LatteLogger.e("response",s);
                        if (SUCCESS!=null) {
                            SUCCESS.onSuccess(s);
                        }
                        onRequestFinish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (FAILURE!=null) {
                            FAILURE.onFailure();
                        }
                        if (REQUEST!=null) {
                            REQUEST.onRequestEnd();
                        }
                        onRequestFinish();
                    }
                });

    }

    private void onRequestFinish() {
        if (LOADER_STYLE != null) {
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            RestCreator.getParams().clear();
                            AppLoader.stopLoading();
                        }
                    });
        }
    }

    /**-------------------以下为暴露的方法------------------------------*/
    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upLoad(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR).handleDownload();
    }

    /**----------------------构建者------------------------------------*/
    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }


    /**-----------------------用法--------------------------------------*/

//      AppConfig.init(this)
//            .withIcon(new FontAwesomeModule())
//            .withIcon(new FontEcModule())
//            .withLoaderDelayed(1000)
//            .withApiHost("Http://127.0.0.1")
//            .withInterceptor(new DebugInterceptor("test", R.raw.test))
//            .withWeChatAppId("s")
//            .withWeChatAppSecret("你的微信AppSecret")
//            .withJavascriptInterface("latte")
//            .withWebEvent("test", new TestEvent())
//            .withWebEvent("share", new ShareEvent())
//            .configure();

//    private void getAuth(String authUrl) {
//        RestClient
//                .builder()
//                .url(authUrl)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//
//                        final JSONObject authObj = JSON.parseObject(response);
//                        final String accessToken = authObj.getString("access_token");
//                        final String openId = authObj.getString("openid");
//
//                        final StringBuilder userInfoUrl = new StringBuilder();
//                        userInfoUrl
//                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
//                                .append(accessToken)
//                                .append("&openid=")
//                                .append(openId)
//                                .append("&lang=")
//                                .append("zh_CN");
//
//                        LatteLogger.d("userInfoUrl", userInfoUrl.toString());
//                        getUserInfo(userInfoUrl.toString());
//
//                    }
//                })
//                .build()
//                .get();
//    }

}













