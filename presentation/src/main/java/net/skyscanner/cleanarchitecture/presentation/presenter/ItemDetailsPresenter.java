package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItem;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;

import rx.Subscriber;
import rx.Subscription;

public class ItemDetailsPresenter extends AbstractPresenter<ItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mSubscription;

    public ItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    void onTakeView() {
        mSubscription = mGetItem.execute(new Subscriber<Item>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Item item) {
                mView.showItem(ItemModel.from(item));
            }
        });
    }

    @Override
    void onDropView() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    public interface View {
        void showItem(ItemModel itemModel);
    }
}
