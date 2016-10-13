package com.yoelglus.presentation.patterns.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.memoizrlabs.Scope;
import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;

import rx.functions.Action1;

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
        mAddButton = findViewById(R.id.add_button);
        RxView.clicks(mAddButton).subscribe(mPresenter.onAddButtonClicked());
        RxView.clicks(findViewById(R.id.cancel_button)).subscribe(mPresenter.onDismissButtonClicked());
        RxTextView.textChanges((TextView) findViewById(R.id.content)).subscribe(mPresenter.onContentTextChanged());
        RxTextView.textChanges((TextView) findViewById(R.id.detail)).subscribe(mPresenter.onDetailTextChanged());

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
    public Action1<Boolean> addButtonEnabled() {
        //noinspection unchecked
        return (Action1<Boolean>) RxView.enabled(mAddButton);
    }

    @Override
    public Action1<Void> dismissView() {
        return aVoid -> finish();
    }
}
