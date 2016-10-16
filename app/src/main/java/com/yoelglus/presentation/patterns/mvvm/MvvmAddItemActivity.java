package com.yoelglus.presentation.patterns.mvvm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;

import rx.Observable;
import rx.internal.util.SubscriptionList;

public class MvvmAddItemActivity extends AppCompatActivity {

    private AddItemViewModel mAddItemViewModel;
    private SubscriptionList mSubscriptionList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mAddItemViewModel = Shank.provideNew(AddItemViewModel.class, this);

        bindViewModel();

        mAddItemViewModel.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddItemViewModel.onStop();
        mSubscriptionList.unsubscribe();
    }

    private void bindViewModel() {
        mSubscriptionList = new SubscriptionList();
        mSubscriptionList.add(RxView.clicks(findViewById(R.id.add_button))
                .subscribe(aVoid -> mAddItemViewModel.addItemClicked()));
        mSubscriptionList.add(RxView.clicks(findViewById(R.id.cancel_button))
                .subscribe(aVoid -> mAddItemViewModel.cancelClicked()));
        mSubscriptionList.add(getTextChangeObservable(R.id.content).subscribe(mAddItemViewModel::contentTextChanged));
        mSubscriptionList.add(getTextChangeObservable(R.id.detail).subscribe(mAddItemViewModel::detailTextChanged));
        mSubscriptionList.add(mAddItemViewModel.addButtonEnabled()
                .subscribe(RxView.enabled(findViewById(R.id.add_button))));
    }

    @NonNull
    private Observable<String> getTextChangeObservable(int viewId) {
        return RxTextView.textChangeEvents((EditText) findViewById(viewId))
                .map(textViewTextChangeEvent -> textViewTextChangeEvent.text().toString());
    }
}
