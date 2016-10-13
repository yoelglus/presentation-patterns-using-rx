package com.yoelglus.presentation.patterns.navigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveAddItemActivity;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveItemDetailActivity;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveItemDetailFragment;
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
        mActivity.startActivity(new Intent(mActivity, MvpPassiveAddItemActivity.class));
    }

    @Override
    public void navigateToItem(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(MvpPassiveItemDetailFragment.ARG_ITEM_ID, id);
            MvpPassiveItemDetailFragment fragment = new MvpPassiveItemDetailFragment();
            fragment.setArguments(arguments);
            mActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(mActivity, MvpPassiveItemDetailActivity.class);
            intent.putExtra(MvpPassiveItemDetailFragment.ARG_ITEM_ID, id);
            mActivity.startActivity(intent);
        }

    }

    @Override
    public void closeCurrentScreen() {
        mActivity.finish();
    }
}
