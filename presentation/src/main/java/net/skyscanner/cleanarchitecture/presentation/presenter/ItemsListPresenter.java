package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;
import net.skyscanner.cleanarchitecture.presentation.navigator.Navigator;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

public class ItemsListPresenter extends AbstractPresenter<ItemsListPresenter.View> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private Subscription mGetItemsSubscription;

    public ItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    public void onAddItemClicked() {
        mNavigator.navigateToAddItem();
    }

    public void onItemClicked(String id) {
        mNavigator.navigateToItem(id);
    }

    @Override
    void onTakeView() {
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

    @Override
    void onDropView() {
        mGetItemsSubscription.unsubscribe();
    }

    public interface View {
        void showItems(List<ItemModel> itemModel);
    }
}
