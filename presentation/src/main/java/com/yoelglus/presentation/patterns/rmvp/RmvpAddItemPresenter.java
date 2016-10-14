package com.yoelglus.presentation.patterns.rmvp;


import com.yoelglus.presentation.patterns.data.ItemsRepository;

import rx.Observable;
import rx.Scheduler;
import rx.internal.util.SubscriptionList;

public class RmvpAddItemPresenter extends AbstractPresenter<RmvpAddItemPresenter.View> {

    private ItemsRepository mItemsRepository;
    private Scheduler mIoScheduler;
    private Scheduler mMainScheduler;
    private SubscriptionList mSubscriptionList = new SubscriptionList();

    public RmvpAddItemPresenter(ItemsRepository itemsRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        mItemsRepository = itemsRepository;
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    @Override
    public void onTakeView() {

        Observable<ItemToAdd> addEnabled = Observable.combineLatest(mView.contentTextChanged(),
                mView.detailTextChanged(),
                ItemToAdd::new).doOnNext(itemToAdd -> mView.setAddButtonEnabled(itemToAdd.valid()));

        mSubscriptionList.add(mView.addButtonClicks()
                .withLatestFrom(addEnabled, (aVoid, itemToAdd) -> itemToAdd)
                .subscribe(this::addItem));

        mSubscriptionList.add(mView.cancelButtonClicks().subscribe(aVoid -> mView.dismissView()));
    }

    @Override
    public void onDropView() {
        mSubscriptionList.unsubscribe();
    }

    private void addItem(ItemToAdd itemToAdd) {
        mItemsRepository.addItem(itemToAdd.content, itemToAdd.details)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .subscribe(s -> mView.dismissView());
    }

    private static class ItemToAdd {
        private String content;
        private String details;

        private ItemToAdd(String content, String details) {
            this.content = content;
            this.details = details;
        }

        private boolean valid() {
            return content.length() > 0 && details.length() > 0;
        }
    }

    public interface View {
        void setAddButtonEnabled(boolean enabled);

        Observable<String> contentTextChanged();

        Observable<String> detailTextChanged();

        Observable<Void> addButtonClicks();

        Observable<Void> cancelButtonClicks();

        void dismissView();
    }
}
