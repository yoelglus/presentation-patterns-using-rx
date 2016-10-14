package com.yoelglus.presentation.patterns.domain.usecases;

import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;
import com.yoelglus.presentation.patterns.entities.Item;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class GetItems extends AbstractUseCase<List<Item>> {

    private ItemsRepository mItemsRepository;

    public GetItems(Scheduler ioScheduler, Scheduler mainScheduler, ItemsRepository itemsRepository) {
        super(ioScheduler, mainScheduler);
        mItemsRepository = itemsRepository;
    }

    @Override
    protected Observable<List<Item>> getObservable() {
        return mItemsRepository.getItems();
    }
}
