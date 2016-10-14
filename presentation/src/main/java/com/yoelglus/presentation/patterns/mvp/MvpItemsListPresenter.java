package com.yoelglus.presentation.patterns.mvp;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
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

    @Override
    public void onTakeView() {
        mGetItemsSubscription = mGetItems.execute(null).map(mItemModelsMapper::map).subscribe(mView.showItems());
    }

    @Override
    public void onDropView() {
        mGetItemsSubscription.unsubscribe();
    }

    Action1<Void> onAddItemClicked() {
        return aVoid -> mNavigator.navigateToAddItem();
    }

    Action1<String> onItemClicked() {
        return mNavigator::navigateToItem;
    }

    public interface View {
        Action1<List<ItemModel>> showItems();
    }
}
