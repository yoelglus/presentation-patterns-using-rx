package com.yoelglus.presentation.patterns.mvppassive;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import java.util.List;

import rx.Observable;
import rx.internal.util.SubscriptionList;

public class MvpPassiveItemsListPresenter extends AbstractPresenter<MvpPassiveItemsListPresenter.View> {

    private GetItems mGetItems;
    private Navigator mNavigator;
    private SubscriptionList mSubscriptionList;

    public MvpPassiveItemsListPresenter(GetItems getItems, Navigator navigator) {
        mGetItems = getItems;
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

        mSubscriptionList.add(mGetItems.execute().subscribe(items -> {
            mView.showItems(ItemModelsMapper.map(items));
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
