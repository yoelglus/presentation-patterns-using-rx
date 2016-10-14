package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.data.ItemsRepository;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class AddItemViewModel extends AbstractViewModel {

    private PublishSubject<Boolean> mAddButtonEnabledSubject = PublishSubject.create();
    private PublishSubject<Void> mDismissSubject = PublishSubject.create();
    private String mContentText = "";
    private Subscription mAddItemSubscription;
    private String mDetailText = "";
    private ItemsRepository mItemsRepository;
    private Scheduler mIoScheduler;
    private Scheduler mMainScheduler;

    public AddItemViewModel(ItemsRepository itemsRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        mItemsRepository = itemsRepository;
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
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

    public void contentTextChanged(String contentText) {
        mContentText = contentText;
        updateAddButtonState();
    }

    public void detailTextChanged(String detailText) {
        mDetailText = detailText;
        updateAddButtonState();
    }

    public void addItemClicked() {
        mAddItemSubscription = mItemsRepository.addItem(mContentText, mDetailText)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .subscribe(s -> mDismissSubject.onNext(null));
    }

    public void cancelClicked() {
        mDismissSubject.onNext(null);
    }

    private void updateAddButtonState() {
        mAddButtonEnabledSubject.onNext(mContentText.length() > 0 && mDetailText.length() > 0);
    }

}
