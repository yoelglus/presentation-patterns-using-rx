package com.yoelglus.presentation.patterns.domain.usecases;

import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;

import rx.Observable;
import rx.Scheduler;

public class AddItem extends AbstractUseCase<String, AddItem.AddItemParam> {

    private final ItemsRepository mItemsRepository;

    public AddItem(Scheduler ioScheduler,
                   Scheduler mainScheduler,
                   ItemsRepository itemsRepository) {
        super(ioScheduler, mainScheduler);
        mItemsRepository = itemsRepository;
    }

    @Override
    public Observable<String> getObservable() {
        return mItemsRepository.addItem(mParam.mContent, mParam.mDetail);
    }

    public static class AddItemParam {
        private String mContent;
        private String mDetail;

        public AddItemParam(String content, String detail) {
            mContent = content;
            mDetail = detail;
        }
    }
}
