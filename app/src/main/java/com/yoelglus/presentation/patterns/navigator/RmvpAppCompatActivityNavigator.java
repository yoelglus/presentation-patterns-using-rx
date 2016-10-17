package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.rmvp.RmvpAddItemActivity;


public class RmvpAppCompatActivityNavigator implements Navigator {

    private AppCompatActivity activity;

    public RmvpAppCompatActivityNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void navigateToAddItem() {
        activity.startActivity(new Intent(activity, RmvpAddItemActivity.class));
    }

    @Override
    public void closeCurrentScreen() {
        activity.finish();
    }
}
