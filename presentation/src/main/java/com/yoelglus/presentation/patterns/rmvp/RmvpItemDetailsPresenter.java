package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Scheduler;
import rx.Subscription;

public class RmvpItemDetailsPresenter extends AbstractPresenter<RmvpItemDetailsPresenter.View> {

    private ItemsRepository mItemsRepository;
    private Subscription mGetItemSubscription;
    private String mItemId;
    private Scheduler mIoScheduler;
    private rx.Scheduler mMainScheduler;

    public RmvpItemDetailsPresenter(ItemsRepository itemsRepository,
                                    String itemId,
                                    Scheduler ioScheduler,
                                    Scheduler mainScheduler) {
        mItemsRepository = itemsRepository;
        mItemId = itemId;
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }


    @Override
    public void onTakeView() {
        mGetItemSubscription = mItemsRepository.getItem(mItemId)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .map(ItemModel::from)
                .subscribe(mView::showItem);
    }

    @Override
    public void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
        void showItem(ItemModel itemModel);
    }
}
