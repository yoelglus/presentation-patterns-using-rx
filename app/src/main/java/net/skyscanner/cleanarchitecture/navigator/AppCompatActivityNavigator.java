package net.skyscanner.cleanarchitecture.navigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.skyscanner.cleanarchitecture.ItemDetailActivity;
import net.skyscanner.cleanarchitecture.ItemDetailFragment;
import net.skyscanner.cleanarchitecture.R;
import net.skyscanner.cleanarchitecture.presentation.navigator.Navigator;


public class AppCompatActivityNavigator implements Navigator {

    private AppCompatActivity mActivity;
    private boolean mTwoPane;

    public AppCompatActivityNavigator(AppCompatActivity activity, boolean twoPane) {
        mActivity = activity;
        mTwoPane = twoPane;
    }

    @Override
    public void navigateToAddItem() {

    }

    @Override
    public void navigateToItem(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            mActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(mActivity, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            mActivity.startActivity(intent);
        }

    }
}
