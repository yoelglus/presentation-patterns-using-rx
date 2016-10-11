package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;
import net.skyscanner.cleanarchitecture.presentation.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.SubscriptionList;

public class ItemsListPresenter extends AbstractPresenter<ItemsListPresenter.View> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private SubscriptionList mSubscriptionList;

    public ItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    @Override
    void onTakeView() {
        mSubscriptionList = new SubscriptionList();
        mSubscriptionList.add(mView.addItemClicks().subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mNavigator.navigateToAddItem();
            }
        }));

        mSubscriptionList.add(mView.itemClicks().subscribe(new Action1<String>() {
            @Override
            public void call(String id) {
                mNavigator.navigateToItem(id);
            }
        }));

        mSubscriptionList.add(mGetItems.execute().map(new Func1<List<Item>, List<ItemModel>>() {
            @Override
            public List<ItemModel> call(List<Item> items) {
                return mItemModelsMapper.map(items);
            }
        }).subscribe(mView.showItems()));
    }

    @Override
    void onDropView() {
        mSubscriptionList.unsubscribe();
        mSubscriptionList = null;
    }

    public interface View {
        Action1<List<ItemModel>> showItems();

        Observable<Void> addItemClicks();

        Observable<String> itemClicks();
    }
}
