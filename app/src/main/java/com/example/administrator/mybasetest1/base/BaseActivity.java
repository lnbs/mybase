package com.example.administrator.mybasetest1.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.mybasetest1.R;
import com.example.administrator.mybasetest1.base.mvpbase.BaseView;
import com.example.administrator.mybasetest1.mvp.presenter.PermissionListener;
import com.example.administrator.mybasetest1.utils.activity.AppActivityManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/11/13.
 * 沉浸式，网络请求，图片框架,黄油刀，权限管理
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    public Unbinder unbinder;
    private static final int CODE_REQUEST_PERMISSION = 1;
    private static PermissionListener mPermissionListener;

    /**---------------------需要实现的方法---------------------*/
    /**
     * 第一步设置布局id
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 第二步初始化页面布局空间
     */
    protected abstract void initView();
    /**----------------------activity生命周期-------------------------*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        AppActivityManager.getInstance().addActivity(this);//新建时添加到栈
        setContentView(setLayoutId());
        unbinder = ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void onDestroy() {
        if (unbinder!=null) {
            unbinder.unbind();
        }
        AppActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
        fixInputMethodManagerLeak(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODE_REQUEST_PERMISSION:
                if (grantResults.length>0) {
                    List<String> deniedPermissions=new ArrayList<>();
                    for (int i = 0,j=grantResults.length; i < j; i++) {
                        int grantResult = grantResults[i];
                        if (grantResult!= PackageManager.PERMISSION_GRANTED) {
                            String permission=permissions[i];
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()){
                        mPermissionListener.onGranted();
                    }else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;
                default:
                    break;
        }
    }

    /**---------------------一些基本方法---------------------*/
    //获取状态栏高度
    protected int getStatusBarHeight(){
        try {
            Class<?> aClass = Class.forName("com.android.internal.R$dimen");
            Object o = aClass.newInstance();
            Field status_bar_height = aClass.getField("status_bar_height");
            Object o1 = status_bar_height.get(o);
            int height = Integer.parseInt(o1.toString());
            return getResources().getDimensionPixelOffset(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void setStatus(){
        final ViewGroup barlayout=(ViewGroup)findViewById(R.id.bar_layout);
        if (null!=barlayout) {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
//                //设置状态栏透明
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                //设置导航栏透明
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                final int statusBarHeight=getStatusBarHeight();
                barlayout.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = barlayout.getHeight();
                        ViewGroup.LayoutParams layoutParams = barlayout.getLayoutParams();
                        layoutParams.height=height+statusBarHeight;
                        barlayout.setLayoutParams(layoutParams);
                    }
                });
            }
        }
    }

    public void fullScreen(Activity activity,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
    }

    protected void openActivty(Class<?> mclass){
        Intent intent=new Intent(this,mclass);
        startActivity(intent);
    }

    /**
     * 设置退出 动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
    }

    public static void requestPermissions(String[] permissions,PermissionListener listener){
        Activity topActivity = AppActivityManager.getInstance().getTopActivity();
        if (null==topActivity) {
            return;
        }
        mPermissionListener = listener;
        List<String> permissionList=new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity,permission)!= PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(topActivity,permissionList.toArray(new String[permissionList.size()]),CODE_REQUEST_PERMISSION);
        }else {
            mPermissionListener.onGranted();
        }

    }

    /**
     * 解决InputMethodManager内存泄露现象
     */
    private static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (String param : arr) {
            try {
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get
                            .getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        /*if (QLog.isColorLevel()) {
                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
                        }*/
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**--------------------------------实现的方法---------------------------------------*/
    @Override
    public void showToast(String msg) {
        ToastUtils.showShortSafe(msg);
    }

}
















