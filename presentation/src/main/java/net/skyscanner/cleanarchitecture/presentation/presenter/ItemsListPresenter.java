package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

public class ItemsListPresenter {

    private View mView;

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Subscription mGetItemsSubscription;

    public ItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
    }

    public void takeView(View view) {
        mView = view;
        mGetItemsSubscription = mGetItems.execute(new Subscriber<List<Item>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Item> items) {
                mView.showItems(mItemModelsMapper.map(items));
            }
        });
    }

    public void dropView(View view) {
        if (mView.equals(view)) {
            mView = null;
            if (!mGetItemsSubscription.isUnsubscribed()) {
                mGetItemsSubscription.unsubscribe();
            }
        }
    }

    public interface View {
        void showItems(List<ItemModel> itemModel);
    }
}
