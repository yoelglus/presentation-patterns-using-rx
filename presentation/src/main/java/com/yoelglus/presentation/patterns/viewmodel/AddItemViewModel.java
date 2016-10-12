package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.domain.usecases.AddItem;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class AddItemViewModel extends AbstractViewModel {

    private PublishSubject<Boolean> mAddButtonEnabledSubject = PublishSubject.create();
    private PublishSubject<Void> mDismissSubject = PublishSubject.create();
    private String mContentText = "";
    private Subscription mAddItemSubscription;
    private String mDetailText = "";
    private AddItem mAddItem;

    private Action1<String> mContentChangedAction = new Action1<String>() {
        @Override
        public void call(String contentText) {
            mContentText = contentText;
            updateAddButtonState();
        }
    };

    private Action1<String> mDetailChangedAction = new Action1<String>() {
        @Override
        public void call(String detailText) {
            mDetailText = detailText;
            updateAddButtonState();
        }
    };
    private Action1<Void> mAddItemClicks = new Action1<Void>() {
        @Override
        public void call(Void aVoid) {
            mAddItemSubscription = mAddItem.execute(mContentText, mDetailText).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    mDismissSubject.onNext(null);
                }
            });
        }
    };
    private Action1<Void> mCancelClicks = new Action1<Void>() {
        @Override
        public void call(Void aVoid) {
            mDismissSubject.onNext(null);
        }
    };

    public AddItemViewModel(AddItem addItem) {
        mAddItem = addItem;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateAddButtonState();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAddItemSubscription != null) {
            mAddItemSubscription.unsubscribe();
            mAddItemSubscription = null;
        }
    }

    public Observable<Boolean> addButtonEnabled() {
        return mAddButtonEnabledSubject;
    }

    public Observable<Void> dismiss() {
        return mDismissSubject;
    }

    public Action1<String> contentTextChanged() {
        return mContentChangedAction;
    }

    public Action1<String> detailTextChanged() {
        return mDetailChangedAction;
    }

    public Action1<Void> addItemClicks() {
        return mAddItemClicks;
    }

    public Action1<Void> cancelClicks() {
        return mCancelClicks;
    }

    private void updateAddButtonState() {
        mAddButtonEnabledSubject.onNext(mContentText.length() > 0 && mDetailText.length() > 0);
    }

}
