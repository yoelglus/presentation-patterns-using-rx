package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class AddItemViewModel extends AbstractViewModel {

    private PublishSubject<Boolean> mAddButtonEnabledSubject = PublishSubject.create();
    private String mContentText = "";
    private Subscription mAddItemSubscription;
    private String mDetailText = "";
    private ItemsRepository mItemsRepository;
    private Navigator mNavigator;
    private Scheduler mIoScheduler;
    private Scheduler mMainScheduler;

    public AddItemViewModel(ItemsRepository itemsRepository,
                            Navigator navigator,
                            Scheduler ioScheduler,
                            Scheduler mainScheduler) {
        mItemsRepository = itemsRepository;
        mNavigator = navigator;
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

    Observable<Boolean> addButtonEnabled() {
        return mAddButtonEnabledSubject.asObservable();
    }

    void contentTextChanged(String contentText) {
        mContentText = contentText;
        updateAddButtonState();
    }

    void detailTextChanged(String detailText) {
        mDetailText = detailText;
        updateAddButtonState();
    }

    void addItemClicked() {
        mAddItemSubscription = mItemsRepository.addItem(mContentText, mDetailText)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .subscribe(s -> mNavigator.closeCurrentScreen());
    }

    void cancelClicked() {
        mNavigator.closeCurrentScreen();
    }

    private void updateAddButtonState() {
        mAddButtonEnabledSubject.onNext(mContentText.length() > 0 && mDetailText.length() > 0);
    }

}
