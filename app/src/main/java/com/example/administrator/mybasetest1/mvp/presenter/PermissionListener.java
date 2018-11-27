package com.example.administrator.mybasetest1.mvp.presenter;

import java.util.List;

/**
 * Created by Administrator on 2018/11/14.
 * 权限回调接口
 */

public interface PermissionListener {
    void onGranted();
    void onDenied(List<String> deniedPermissions);
}
