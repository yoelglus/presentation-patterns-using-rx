package net.skyscanner.cleanarchitecture.domain.usecases;

import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

public class AddItem extends AbstractUseCase<String> {

    private final ItemsRepository mItemsRepository;
    private String mContent;
    private String mDetail;

    public AddItem(Scheduler ioScheduler,
                   Scheduler mainScheduler,
                   ItemsRepository itemsRepository) {
        super(ioScheduler, mainScheduler);
        mItemsRepository = itemsRepository;
    }

    public Subscription execute(Subscriber<String> subscriber, String content, String detail) {
        mContent = content;
        mDetail = detail;
        return execute(subscriber);
    }

    @Override
    public Observable<String> getObservable() {
        return mItemsRepository.addItem(mContent, mDetail);
    }
}
