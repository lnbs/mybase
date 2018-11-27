package com.example.administrator.mybasetest1.utils.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by Administrator on 2018/11/14.
 */

public class AppActivityManager {
    private static Stack<Activity> mStack;

    /**-------------设置单例模式-------------------*/
    private AppActivityManager(){
        mStack=new Stack<>();
    }
    private static class AppActivityManagerHolder{
        private static final AppActivityManager INSTANCE=new AppActivityManager();
    }
    public static AppActivityManager getInstance(){
        return AppActivityManagerHolder.INSTANCE;
    }

    /**---------------ACTIVITY添加删除等操作api---------------------------*/
    public void addActivity(Activity activity){
        if (mStack==null) {
            mStack=new Stack<>();
        }
        mStack.add(activity);
    }

    /**
     *
     * @param activity
     */
    public void removeActivity(Activity activity){
        if (mStack!=null) {
            mStack.remove(activity);
        }
    }

    public Activity getTopActivity(){
        if (mStack!=null) {
            return mStack.lastElement();
        }else {
            return null;
        }
    }

    public void killTopActivity(){
        if (mStack!=null) {
            Activity activity = mStack.lastElement();
            killActivity(activity);
        }
    }

    private void killActivity(Activity activity) {
        if (activity!=null) {
            mStack.remove(activity);
        }
        activity.finish();
    }

    public void killActivity(Class<?> cls){
        for (Activity activity : mStack) {
            if (activity.getClass().equals(cls)) {
                killActivity(activity);
            }
        }
    }

    private void killAllActivity(){
        if (mStack!=null) {
            for (int i = 0,j=mStack.size(); i < j; i++) {
                if (null!=mStack.get(i)) {
                    mStack.get(i).finish();
                }
            }
            mStack.clear();
        }
    }

    public void AppExit(Context context){
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



















