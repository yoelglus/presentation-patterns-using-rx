package com.yoelglus.presentation.patterns.mvppassiverx;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
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
        mSubscriptionList.add(mView.addItemClicks().subscribe(aVoid -> {
            mNavigator.navigateToAddItem();
        }));

        mSubscriptionList.add(mView.itemClicks().subscribe(id -> {
            mNavigator.navigateToItem(id);
        }));

        mSubscriptionList.add(mGetItems.execute().map(mItemModelsMapper::map).subscribe(mView.showItems()));
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
