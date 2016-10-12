package com.yoelglus.presentation.patterns.mvp;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class MvpItemsListPresenter extends AbstractPresenter<MvpItemsListPresenter.View> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private Subscription mGetItemsSubscription;

    public MvpItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    void onAddItemClicked() {
        mNavigator.navigateToAddItem();
    }

    void onItemClicked(String id) {
        mNavigator.navigateToItem(id);
    }

    @Override
    public void onTakeView() {
        mGetItemsSubscription = mGetItems.execute().subscribe(new Action1<List<Item>>() {
            @Override
            public void call(List<Item> items) {
                mView.showItems(mItemModelsMapper.map(items));
            }
        });
    }

    @Override
    public void onDropView() {
        mGetItemsSubscription.unsubscribe();
    }

    public interface View {
        void showItems(List<ItemModel> itemModel);
    }
}
