package net.skyscanner.cleanarchitecture.presentation.viewmodel;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItem;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ItemDetailViewModel extends AbstractViewModel {

    private GetItem mGetItem;
    private PublishSubject<ItemModel> mItemModelSubject = PublishSubject.create();
    private Subscription mGetItemSubscription;

    public ItemDetailViewModel(GetItem getItem) {
        mGetItem = getItem;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemSubscription = mGetItem.execute(new Subscriber<Item>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Item item) {
                mItemModelSubject.onNext(ItemModel.from(item));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mGetItemSubscription.unsubscribe();
    }

    public Observable<ItemModel> itemModel() {
        return mItemModelSubject;
    }
}
