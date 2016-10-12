package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.mvp.MvpAddItemActivity;
import com.yoelglus.presentation.patterns.mvp.MvpItemDetailActivity;
import com.yoelglus.presentation.patterns.mvp.MvpItemDetailFragment;
import com.yoelglus.presentation.patterns.R;


public class AppCompatActivityNavigator implements Navigator {

    private AppCompatActivity mActivity;
    private boolean mTwoPane;

    public AppCompatActivityNavigator(AppCompatActivity activity, boolean twoPane) {
        mActivity = activity;
        mTwoPane = twoPane;
    }

    @Override
    public void navigateToAddItem() {
        mActivity.startActivity(new Intent(mActivity, MvpAddItemActivity.class));
    }

    @Override
    public void navigateToItem(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(MvpItemDetailFragment.ARG_ITEM_ID, id);
            MvpItemDetailFragment fragment = new MvpItemDetailFragment();
            fragment.setArguments(arguments);
            mActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(mActivity, MvpItemDetailActivity.class);
            intent.putExtra(MvpItemDetailFragment.ARG_ITEM_ID, id);
            mActivity.startActivity(intent);
        }

    }
}
