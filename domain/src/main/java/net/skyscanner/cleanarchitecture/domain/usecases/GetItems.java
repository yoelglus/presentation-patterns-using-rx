package net.skyscanner.cleanarchitecture.domain.usecases;

import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;
import net.skyscanner.cleanarchitecture.entities.Item;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

public class GetItems extends AbstractUseCase<List<Item>> {

    private ItemsRepository mItemsRepository;

    public GetItems(Scheduler ioScheduler, Scheduler mainScheduler, ItemsRepository itemsRepository) {
        super(ioScheduler, mainScheduler);
        mItemsRepository = itemsRepository;
    }

    @Override
    public Observable<List<Item>> getObservable() {
        return mItemsRepository.getItems();
    }
}
