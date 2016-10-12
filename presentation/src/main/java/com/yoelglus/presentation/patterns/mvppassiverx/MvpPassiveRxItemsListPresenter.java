package com.yoelglus.presentation.patterns.mvppassiverx;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.SubscriptionList;

public class MvpPassiveRxItemsListPresenter extends AbstractPresenter<MvpPassiveRxItemsListPresenter.View> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private SubscriptionList mSubscriptionList;

    public MvpPassiveRxItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    @Override
    public void onTakeView() {
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
    public void onDropView() {
        mSubscriptionList.unsubscribe();
        mSubscriptionList = null;
    }

    public interface View {
        Action1<List<ItemModel>> showItems();

        Observable<Void> addItemClicks();

        Observable<String> itemClicks();
    }
}
