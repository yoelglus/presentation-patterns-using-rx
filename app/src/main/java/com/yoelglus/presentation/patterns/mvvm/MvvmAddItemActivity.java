package com.yoelglus.presentation.patterns.mvvm;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.yoelglus.presentation.patterns.R;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import rx.Observable;
import rx.internal.util.SubscriptionList;

import static com.memoizrlabs.Shank.provideNew;

public class MvvmAddItemActivity extends AppCompatActivity {

    private AddItemViewModel addItemViewModel;
    private SubscriptionList subscriptionList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemViewModel = provideNew(AddItemViewModel.class, this);

        bindViewModel();

        addItemViewModel.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addItemViewModel.onStop();
        subscriptionList.unsubscribe();
    }

    private void bindViewModel() {
        subscriptionList = new SubscriptionList();
        subscriptionList.add(RxView.clicks(findViewById(R.id.add_button))
                .subscribe(aVoid -> addItemViewModel.addItemClicked()));
        subscriptionList.add(RxView.clicks(findViewById(R.id.cancel_button))
                .subscribe(aVoid -> addItemViewModel.cancelClicked()));
        subscriptionList.add(getTextChangeObservable(R.id.content).subscribe(addItemViewModel::contentTextChanged));
        subscriptionList.add(getTextChangeObservable(R.id.detail).subscribe(addItemViewModel::detailTextChanged));
        subscriptionList.add(addItemViewModel.addButtonEnabled()
                .subscribe(RxView.enabled(findViewById(R.id.add_button))));
    }

    @NonNull
    private Observable<String> getTextChangeObservable(int viewId) {
        return RxTextView.textChangeEvents((EditText) findViewById(viewId))
                .map(textViewTextChangeEvent -> textViewTextChangeEvent.text().toString());
    }
}
