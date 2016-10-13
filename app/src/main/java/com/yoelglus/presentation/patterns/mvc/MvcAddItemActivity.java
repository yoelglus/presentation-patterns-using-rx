package com.yoelglus.presentation.patterns.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;

import rx.Subscription;

public class MvcAddItemActivity extends AppCompatActivity {

    private Subscription mModelSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MvcAddItemController controller = Shank.provideSingleton(MvcAddItemController.class, this, false);
        MvcAddItemModel model = Shank.provideSingleton(MvcAddItemModel.class);
        setContentView(R.layout.activity_add_item);
        RxView.clicks(findViewById(R.id.cancel_button)).subscribe(controller.dismissButtonClicked());
        RxTextView.textChanges((EditText) findViewById(R.id.content)).subscribe(controller.contentTextChanged());
        RxTextView.textChanges((EditText) findViewById(R.id.detail)).subscribe(controller.detailTextChanged());
        View addButton = findViewById(R.id.add_button);
        RxView.clicks(addButton).subscribe(controller.addButtonClicked());

        mModelSubscription = model.addItemEnabled().subscribe(RxView.enabled(addButton));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mModelSubscription.unsubscribe();
        }
    }
}
