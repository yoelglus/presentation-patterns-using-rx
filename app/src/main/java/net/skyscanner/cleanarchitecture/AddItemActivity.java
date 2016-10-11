package net.skyscanner.cleanarchitecture;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.memoizrlabs.Scope;
import com.memoizrlabs.Shank;

import net.skyscanner.cleanarchitecture.presentation.presenter.AddItemPresenter;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class AddItemActivity extends AppCompatActivity implements AddItemPresenter.View {

    private Scope mScope;
    private AddItemPresenter mPresenter;
    private View mAddButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScope = Scope.scope(AddItemActivity.class);
        mPresenter = Shank.with(mScope).provideSingleton(AddItemPresenter.class);
        setContentView(R.layout.activity_add_item);
        mAddButton = findViewById(R.id.add_button);
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
    public Action1<Boolean> setAddButtonEnabled() {
        //noinspection unchecked
        return (Action1<Boolean>) RxView.enabled(mAddButton);
    }

    @Override
    public Action1<Void> dismissView() {
        return new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        };
    }

    @NonNull
    private Observable<String> getObservableForTextView(int viewId) {
        return RxTextView.textChangeEvents((TextView) findViewById(viewId))
                .map(new Func1<TextViewTextChangeEvent, String>() {
                    @Override
                    public String call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return textViewTextChangeEvent.text().toString();
                    }
                });
    }
}
