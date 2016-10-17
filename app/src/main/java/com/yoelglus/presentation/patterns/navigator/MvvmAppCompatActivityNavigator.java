package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.mvvm.MvvmAddItemActivity;


public class MvvmAppCompatActivityNavigator implements Navigator {

    private AppCompatActivity activity;

    public MvvmAppCompatActivityNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void navigateToAddItem() {
        activity.startActivity(new Intent(activity, MvvmAddItemActivity.class));
    }

    @Override
    public void closeCurrentScreen() {
        activity.finish();
    }
}
