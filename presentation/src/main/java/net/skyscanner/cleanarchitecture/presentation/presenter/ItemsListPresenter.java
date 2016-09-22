package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;
import net.skyscanner.cleanarchitecture.presentation.model.ItemsListViewModel;
import net.skyscanner.cleanarchitecture.presentation.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

public class ItemsListPresenter extends AbstractPresenter<ItemsListViewModel> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private Subscription mGetItemsSubscription;

    public ItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    @Override
    protected void onSubscribe() {
        mGetItemsSubscription = mGetItems.execute(new Subscriber<List<Item>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Item> items) {
                notifyOnChange(new ItemsListViewModel(mItemModelsMapper.map(items)));
            }
        });
    }

    @Override
    protected void onUnSubscribe() {
        mGetItemsSubscription.unsubscribe();
    }

    public interface View {
        void showItems(List<ItemModel> itemModel);
        Observable<Void> addItemClicks();
        Observable<String> itemClicks();
    }

    public void onItemClicked(String id) {
        mNavigator.navigateToItem(id);
    }

    public void onAddItemClicked() {
        mNavigator.navigateToAddItem();
    }
}
