package com.example.administrator.mybasetest1.di.Model;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.administrator.mybasetest1.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/11/16.
 */

@Module
public class FragmentModel {
    private Fragment mFragment;

    public FragmentModel(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    @PerFragment
    public Fragment provideFragment(){
        return mFragment;
    }

    @Provides
    @PerFragment
    public Activity provideFragmentActivity(){
        return mFragment.getActivity();
    }



}
