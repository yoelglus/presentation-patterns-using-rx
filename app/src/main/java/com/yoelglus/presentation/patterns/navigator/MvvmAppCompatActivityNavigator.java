package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.mvvm.MvvmAddItemActivity;
import com.yoelglus.presentation.patterns.mvvm.MvvmItemDetailActivity;
import com.yoelglus.presentation.patterns.mvvm.MvvmItemDetailFragment;


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
    public void navigateToItem(String id) {
        Intent intent = new Intent(activity, MvvmItemDetailActivity.class);
        intent.putExtra(MvvmItemDetailFragment.ARG_ITEM_ID, id);
        activity.startActivity(intent);
    }

    @Override
    public void closeCurrentScreen() {
        activity.finish();
    }
}
