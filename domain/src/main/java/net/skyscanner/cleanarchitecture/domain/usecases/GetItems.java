package net.skyscanner.cleanarchitecture.domain.usecases;

import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;
import net.skyscanner.cleanarchitecture.entities.Item;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

public class GetItems {

    // State
    private ItemsRepository mItemsRepository;
    private final Scheduler mIoScheduler;
    private final Scheduler mMainScheduler;

    public GetItems(ItemsRepository itemsRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        mItemsRepository = itemsRepository;
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    // region Public methods
    public Subscription execute(Subscriber<List<Item>> subscriber) {
        return mItemsRepository.getItems().subscribeOn(mIoScheduler).observeOn(mMainScheduler).subscribe(subscriber);
    }
// endregion Public methods
}
