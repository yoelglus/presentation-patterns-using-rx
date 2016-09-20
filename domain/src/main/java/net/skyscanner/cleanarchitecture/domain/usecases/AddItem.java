package net.skyscanner.cleanarchitecture.domain.usecases;

import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;

import rx.Observable;
import rx.Scheduler;

public class AddItem extends AbstractUseCase<String> {

    private final ItemsRepository mItemsRepository;
    private final String mContent;
    private final String mDetail;

    public AddItem(Scheduler ioScheduler,
                   Scheduler mainScheduler,
                   ItemsRepository itemsRepository,
                   String content,
                   String detail) {
        super(ioScheduler, mainScheduler);
        mItemsRepository = itemsRepository;
        mContent = content;
        mDetail = detail;
    }

    @Override
    public Observable<String> getObservable() {
        return mItemsRepository.addItem(mContent, mDetail);
    }
}
