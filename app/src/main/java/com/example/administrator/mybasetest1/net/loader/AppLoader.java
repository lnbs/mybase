package com.example.administrator.mybasetest1.net.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.administrator.mybasetest1.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/11/13.
 */

public class AppLoader {
    private static final int LOADER_SIZE_SCALE=8;
    private static final int LOADER_OFFSET_SCALE=10;
    private static final ArrayList<AppCompatDialog> LOADERS=new ArrayList<>();
    private static final String DEFAULT_LOADER=LoaderStyle.BallClipRotatePulseIndicator.name();
    public static void showLoading(Context context, Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    public static void showLoading(Context context,String style){
        final AppCompatDialog dialog=new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView=LoaderCreator.create(style,context);
        dialog.setContentView(avLoadingIndicatorView);
        int deviceWidth= ScreenUtils.getScreenWidth();
        int deviceHeight= ScreenUtils.getScreenHeight();
        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow!=null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width=deviceWidth/LOADER_SIZE_SCALE;
            lp.height=deviceHeight/LOADER_SIZE_SCALE;
            lp.height=lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity= Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog loader : LOADERS) {
            if (loader!=null) {
                if (loader.isShowing()) {
                    loader.cancel();
                }
            }
        }
    }
}
