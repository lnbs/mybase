package com.example.administrator.mybasetest1.net.downlaod;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.administrator.mybasetest1.app.AppConfig;
import com.example.administrator.mybasetest1.net.callback.IRequest;
import com.example.administrator.mybasetest1.net.callback.ISuccess;
import com.example.administrator.mybasetest1.utils.FileUtils;

import org.w3c.dom.ProcessingInstruction;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/11/14.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    SaveFileTask(IRequest REQUEST,ISuccess SUCCESS){
        this.REQUEST=REQUEST;
        this.SUCCESS=SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir= (String) params[0];//缓存目录
        String extension= (String) params[1];//扩展名
        final ResponseBody body= (ResponseBody) params[2];//返回数据
        final String name= (String) params[3];//文件名称
        final InputStream is = body.byteStream();
        if (downloadDir==null||downloadDir.equals("")) {
            downloadDir="down_loads";
        }
        if (extension==null||extension.equals("")) {
            extension="";
        }

        if (name==null) {
            return FileUtils.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtils.writeToDisk(is,downloadDir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS!=null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST!=null) {
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file){
        if (FileUtils.getExtension(file.getPath()).equals("apk")) {
            final Intent install=new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            AppConfig.getApplicationContext().startActivity(install);
        }
    }
}






















