package com.yoelglus.presentation.patterns.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.memoizrlabs.Scope;
import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;

public class MvpAddItemActivity extends AppCompatActivity implements MvpAddItemPresenter.View {

    private Scope mScope;
    private MvpAddItemPresenter mPresenter;
    private View mAddButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScope = Scope.scope(MvpAddItemActivity.class);
        mPresenter = Shank.with(mScope).provideSingleton(MvpAddItemPresenter.class);
        setContentView(R.layout.activity_add_item);
        setUpAddButton();
        setUpDismissButton();
        setUpOnTextChangedEvents();

        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mPresenter.dropView(this);
            mScope.clear();
        }
    }

    @Override
    public void setAddButtonEnabled(boolean enabled) {
        mAddButton.setEnabled(enabled);
    }

    @Override
    public void dismissView() {
        finish();
    }

    private void setUpDismissButton() {
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onDismissButtonClicked();
            }
        });
    }

    private void setUpAddButton() {
        mAddButton = findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onAddButtonClicked();
            }
        });
    }

    private void setUpOnTextChangedEvents() {
        TextView contentTv = (TextView) findViewById(R.id.content);
        contentTv.addTextChangedListener(new TextWatcher() {
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

        TextView detailTv = (TextView) findViewById(R.id.detail);
        detailTv.addTextChangedListener(new TextWatcher() {
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

}
