package com.example.administrator.mybasetest1.di.component;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.example.administrator.mybasetest1.di.Model.FragmentModel;
import com.example.administrator.mybasetest1.di.scope.ContextLife;
import com.example.administrator.mybasetest1.di.scope.PerFragment;
import com.example.administrator.mybasetest1.mvp.view.fragment.FirstFragment;

import dagger.Component;

/**
 * Created by Administrator on 2018/11/16.
 */

@PerFragment
@Component(modules = FragmentModel.class,dependencies = AppComponent.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(FirstFragment fragment);
}
