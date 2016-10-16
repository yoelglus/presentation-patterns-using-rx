package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.rmvp.RmvpAddItemActivity;
import com.yoelglus.presentation.patterns.rmvp.RmvpItemDetailActivity;
import com.yoelglus.presentation.patterns.rmvp.RmvpItemDetailFragment;


public class ReactiveMvpAppCompatActivityNavigator implements Navigator {

    private AppCompatActivity activity;

    public ReactiveMvpAppCompatActivityNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void navigateToAddItem() {
        activity.startActivity(new Intent(activity, RmvpAddItemActivity.class));
    }

    @Override
    public void navigateToItem(String id) {
        Intent intent = new Intent(activity, RmvpItemDetailActivity.class);
        intent.putExtra(RmvpItemDetailFragment.ARG_ITEM_ID, id);
        activity.startActivity(intent);
    }

    @Override
    public void closeCurrentScreen() {
        activity.finish();
    }
}
