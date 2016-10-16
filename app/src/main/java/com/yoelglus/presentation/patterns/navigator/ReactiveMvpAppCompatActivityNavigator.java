package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.rmvp.RmvpAddItemActivity;
import com.yoelglus.presentation.patterns.rmvp.RmvpItemDetailActivity;
import com.yoelglus.presentation.patterns.rmvp.RmvpItemDetailFragment;


public class ReactiveMvpAppCompatActivityNavigator implements Navigator {

    private AppCompatActivity mActivity;

    public ReactiveMvpAppCompatActivityNavigator(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void navigateToAddItem() {
        mActivity.startActivity(new Intent(mActivity, RmvpAddItemActivity.class));
    }

    @Override
    public void navigateToItem(String id) {
        Intent intent = new Intent(mActivity, RmvpItemDetailActivity.class);
        intent.putExtra(RmvpItemDetailFragment.ARG_ITEM_ID, id);
        mActivity.startActivity(intent);
    }

    @Override
    public void closeCurrentScreen() {
        mActivity.finish();
    }
}
