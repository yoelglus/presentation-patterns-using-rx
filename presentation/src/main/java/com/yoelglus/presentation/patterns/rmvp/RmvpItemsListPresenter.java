package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

public class RmvpItemsListPresenter extends AbstractPresenter<RmvpItemsListPresenter.View> {

    private ItemsRepository mItemsRepository;
    private Navigator mNavigator;
    private SubscriptionList mSubscriptionList;

    public RmvpItemsListPresenter(ItemsRepository itemsRepository, Navigator navigator) {
        mItemsRepository = itemsRepository;
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

        mSubscriptionList.add(mItemsRepository.getItems().map(ItemModelsMapper::map).subscribe(mView.showItems()));
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
