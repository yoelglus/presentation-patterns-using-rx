package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.mvvm.MvvmAddItemActivity;
import com.yoelglus.presentation.patterns.mvvm.MvvmItemDetailActivity;
import com.yoelglus.presentation.patterns.mvvm.MvvmItemDetailFragment;


public class MvvmAppCompatActivityNavigator implements Navigator {

    private AppCompatActivity mActivity;

    public MvvmAppCompatActivityNavigator(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void navigateToAddItem() {
        mActivity.startActivity(new Intent(mActivity, MvvmAddItemActivity.class));
    }

    @Override
    public void navigateToItem(String id) {
        Intent intent = new Intent(mActivity, MvvmItemDetailActivity.class);
        intent.putExtra(MvvmItemDetailFragment.ARG_ITEM_ID, id);
        mActivity.startActivity(intent);
    }

    @Override
    public void closeCurrentScreen() {
        mActivity.finish();
    }
}
