package com.yoelglus.presentation.patterns.domain.usecases;

import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;
import com.yoelglus.presentation.patterns.entities.Item;

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
    protected Observable<Item> getObservable() {
        return mItemsRepository.getItem(mItemId);
    }
}
