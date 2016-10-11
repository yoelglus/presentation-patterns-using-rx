package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItem;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class ItemDetailsPresenter extends AbstractPresenter<ItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public ItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    void onTakeView() {
        mGetItemSubscription = mGetItem.execute().map(new Func1<Item, ItemModel>() {
            @Override
            public ItemModel call(Item item) {
                return ItemModel.from(item);
            }
        }).subscribe(mView.showItem());
    }

    @Override
    void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
        Action1<ItemModel> showItem();
    }
}
