package com.yoelglus.presentation.patterns.rmvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;

import rx.Observable;

public class RmvpAddItemActivity extends AppCompatActivity implements RmvpAddItemPresenter.View {

    private RmvpAddItemPresenter mPresenter;
    private View mAddButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = Shank.provideSingleton(RmvpAddItemPresenter.class, this);
        setContentView(R.layout.activity_add_item);
        mAddButton = findViewById(R.id.add_button);
        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mPresenter.dropView(this);
        }
    }

    @Override
    public void setAddButtonEnabled(boolean enabled) {
        RxView.enabled(mAddButton).call(enabled);
    }

    @Override
    public Observable<String> contentTextChanged() {
        return getObservableForTextView(R.id.content);
    }

    @Override
    public Observable<String> detailTextChanged() {
        return getObservableForTextView(R.id.detail);
    }

    @Override
    public Observable<Void> addButtonClicks() {
        return RxView.clicks(mAddButton);
    }

    @Override
    public Observable<Void> cancelButtonClicks() {
        return RxView.clicks(findViewById(R.id.cancel_button));
    }

    @Override
    public void dismissView() {
        finish();
    }

    @NonNull
    private Observable<String> getObservableForTextView(int viewId) {
        return RxTextView.textChangeEvents((TextView) findViewById(viewId))
                .map(textViewTextChangeEvent -> textViewTextChangeEvent.text().toString());
    }
}
