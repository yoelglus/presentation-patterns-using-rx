package com.yoelglus.presentation.patterns.mvpvm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;
import com.yoelglus.presentation.patterns.model.AddItemViewModel;

import rx.Subscription;
import rx.functions.Action1;

public class MvpVmAddItemActivity extends AppCompatActivity {

    private MvpVmAddItemPresenter mPresenter;
    private View mAddButton;
    private Subscription mPresenterSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = Shank.provideSingleton(MvpVmAddItemPresenter.class);
        setContentView(R.layout.activity_add_item);
        mAddButton = findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onAddButtonClicked();
            }
        });
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onCancelButtonClicked();
            }
        });
        mPresenterSubscription = mPresenter.getViewModelObservable().subscribe(new Action1<AddItemViewModel>() {
            @Override
            public void call(AddItemViewModel addItemViewModel) {
                if (addItemViewModel.shouldDismiss()) {
                    finish();
                } else {
                    mAddButton.setEnabled(addItemViewModel.isAddButtonEnabled());
                }
            }
        });

        EditText contentEditText = (EditText) findViewById(R.id.content);
        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.onContentTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText detailEditText = (EditText) findViewById(R.id.detail);
        detailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.onDetailTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mPresenterSubscription.unsubscribe();
        }
    }
}
