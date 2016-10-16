package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Scheduler;
import rx.Subscription;

public class RmvpItemDetailsPresenter extends AbstractPresenter<RmvpItemDetailsPresenter.View> {

    private ItemsRepository itemsRepository;
    private Subscription getItemSubscription;
    private String itemId;
    private Scheduler ioScheduler;
    private rx.Scheduler mainScheduler;

    public RmvpItemDetailsPresenter(ItemsRepository itemsRepository,
                                    String itemId,
                                    Scheduler ioScheduler,
                                    Scheduler mainScheduler) {
        this.itemsRepository = itemsRepository;
        this.itemId = itemId;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }


    @Override
    public void onTakeView() {
        getItemSubscription = itemsRepository.getItem(itemId)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .map(ItemModel::from)
                .subscribe(view::showItem);
    }

    @Override
    public void onDropView() {
        getItemSubscription.unsubscribe();
    }

    public interface View {
        void showItem(ItemModel itemModel);
    }
}
