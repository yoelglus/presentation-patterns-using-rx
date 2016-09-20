package net.skyscanner.cleanarchitecture.domain.usecases;

import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;
import net.skyscanner.cleanarchitecture.entities.Item;

import rx.Observable;
import rx.Scheduler;

public class GetItem extends AbstractUseCase<Item> {
    private final ItemsRepository mItemsRepository;
    private final String mItemId;

    public GetItem(Scheduler ioScheduler, Scheduler mainScheduler, ItemsRepository itemsRepository, String itemId) {
        super(ioScheduler, mainScheduler);
        mItemId = itemId;
        mItemsRepository = itemsRepository;
    }

    @Override
    public Observable<Item> getObservable() {
        return mItemsRepository.getItem(mItemId);
    }
}
