package com.yoelglus.presentation.patterns.mvvm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.yoelglus.presentation.patterns.R;

import rx.Observable;
import rx.internal.util.SubscriptionList;

import static com.jakewharton.rxbinding.view.RxView.clicks;
import static com.jakewharton.rxbinding.view.RxView.enabled;
import static com.jakewharton.rxbinding.widget.RxTextView.textChangeEvents;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        subscriptionList.add(clicks(findViewById(R.id.add_button)).subscribe(aVoid -> addItemViewModel.addItemClicked()));
        subscriptionList.add(clicks(findViewById(R.id.cancel_button)).subscribe(aVoid -> addItemViewModel.cancelClicked()));
        subscriptionList.add(getTextChangeObservable(R.id.content).subscribe(addItemViewModel::contentTextChanged));
        subscriptionList.add(getTextChangeObservable(R.id.detail).subscribe(addItemViewModel::detailTextChanged));
        subscriptionList.add(addItemViewModel.addButtonEnabled().subscribe(enabled(findViewById(R.id.add_button))));
    }

    @NonNull
    private Observable<String> getTextChangeObservable(int viewId) {
        return textChangeEvents((EditText) findViewById(viewId)).map(
                textViewTextChangeEvent -> textViewTextChangeEvent.text().toString());
    }
}
