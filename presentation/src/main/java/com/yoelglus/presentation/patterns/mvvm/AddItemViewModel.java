package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

class AddItemViewModel extends AbstractViewModel {

    private PublishSubject<Boolean> addButtonEnabledSubject = PublishSubject.create();
    private String contentText = "";
    private Subscription addItemSubscription;
    private String detailText = "";
    private ItemsRepository itemsRepository;
    private Navigator navigator;

    AddItemViewModel(ItemsRepository itemsRepository, Navigator navigator) {
        this.itemsRepository = itemsRepository;
        this.navigator = navigator;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateAddButtonState();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (addItemSubscription != null) {
            addItemSubscription.unsubscribe();
            addItemSubscription = null;
        }
    }

    Observable<Boolean> addButtonEnabled() {
        return addButtonEnabledSubject.asObservable();
    }

    void contentTextChanged(String contentText) {
        this.contentText = contentText;
        updateAddButtonState();
    }

    void detailTextChanged(String detailText) {
        this.detailText = detailText;
        updateAddButtonState();
    }

    void addItemClicked() {
        addItemSubscription = itemsRepository.addItem(contentText, detailText)
                .subscribe(s -> navigator.closeCurrentScreen());
    }

    void cancelClicked() {
        navigator.closeCurrentScreen();
    }

    private void updateAddButtonState() {
        addButtonEnabledSubject.onNext(contentText.length() > 0 && detailText.length() > 0);
    }

}
