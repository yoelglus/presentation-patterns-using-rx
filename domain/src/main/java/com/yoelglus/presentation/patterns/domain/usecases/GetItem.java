package com.yoelglus.presentation.patterns.domain.usecases;

import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;
import com.yoelglus.presentation.patterns.entities.Item;

import rx.Observable;
import rx.Scheduler;

public class GetItem extends AbstractUseCase<Item, String> {
    private final ItemsRepository mItemsRepository;

    public GetItem(Scheduler ioScheduler, Scheduler mainScheduler, ItemsRepository itemsRepository) {
        super(ioScheduler, mainScheduler);
        mItemsRepository = itemsRepository;
    }

    @Override
    public Observable<Item> getObservable() {
        return mItemsRepository.getItem(mParam);
    }
}
