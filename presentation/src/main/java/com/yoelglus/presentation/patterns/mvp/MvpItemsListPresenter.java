package com.yoelglus.presentation.patterns.mvp;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

public class MvpItemsListPresenter extends AbstractPresenter<MvpItemsListPresenter.View> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private SubscriptionList mSubscriptionList;

    public MvpItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
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

        mSubscriptionList.add(mGetItems.execute().subscribe(new Action1<List<Item>>() {
            @Override
            public void call(List<Item> items) {
                mView.showItems(mItemModelsMapper.map(items));
            }
        }));
    }

    @Override
    public void onDropView() {
        mSubscriptionList.unsubscribe();
        mSubscriptionList = null;
    }

    public interface View {
        void showItems(List<ItemModel> itemModel);

        Observable<Void> addItemClicks();

        Observable<String> itemClicks();
    }
}
