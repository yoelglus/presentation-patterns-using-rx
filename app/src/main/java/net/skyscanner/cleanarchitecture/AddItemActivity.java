package net.skyscanner.cleanarchitecture;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.memoizrlabs.Shank;

import net.skyscanner.cleanarchitecture.presentation.viewmodel.AddItemViewModel;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.SubscriptionList;

public class AddItemActivity extends AppCompatActivity {

    private AddItemViewModel mAddItemViewModel;
    private SubscriptionList mSubscriptionList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mAddItemViewModel = Shank.provideNew(AddItemViewModel.class);

        bindViewModel();

        mAddItemViewModel.onStart();
    }

    private void bindViewModel() {
        mSubscriptionList = new SubscriptionList();
        mSubscriptionList.add(RxView.clicks(findViewById(R.id.add_button))
                .doOnNext(mAddItemViewModel.addItemClicks())
                .subscribe());
        mSubscriptionList.add(RxView.clicks(findViewById(R.id.cancel_button))
                .doOnNext(mAddItemViewModel.cancelClicks())
                .subscribe());
        mSubscriptionList.add(getTextChangeObservable(R.id.content).doOnNext(mAddItemViewModel.contentTextChanged())
                .subscribe());
        mSubscriptionList.add(getTextChangeObservable(R.id.detail).doOnNext(mAddItemViewModel.detailTextChanged())
                .subscribe());
        mSubscriptionList.add(mAddItemViewModel.dismiss().doOnNext(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        }).subscribe());
        mSubscriptionList.add(mAddItemViewModel.addButtonEnabled()
                .doOnNext(RxView.enabled(findViewById(R.id.add_button)))
                .subscribe());
    }

    @NonNull
    private Observable<String> getTextChangeObservable(int viewId) {
        return RxTextView.textChangeEvents((EditText) findViewById(viewId))
                .map(new Func1<TextViewTextChangeEvent, String>() {
                    @Override
                    public String call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return textViewTextChangeEvent.text().toString();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddItemViewModel.onStop();
        mSubscriptionList.unsubscribe();
    }
}
