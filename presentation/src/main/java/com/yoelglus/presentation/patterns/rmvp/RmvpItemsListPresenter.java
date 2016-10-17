package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.internal.util.SubscriptionList;

class RmvpItemsListPresenter extends AbstractPresenter<RmvpItemsListPresenter.View> {

    private ItemsRepository itemsRepository;
    private Navigator navigator;
    private SubscriptionList subscriptionList;

    RmvpItemsListPresenter(ItemsRepository itemsRepository, Navigator navigator) {
        this.itemsRepository = itemsRepository;
        this.navigator = navigator;
    }

    @Override
    public void onTakeView() {
        subscriptionList = new SubscriptionList();
        subscriptionList.add(view.addItemClicks().subscribe(aVoid -> navigator.navigateToAddItem()));

        subscriptionList.add(itemsRepository.getItems().map(ItemModelsMapper::map).subscribe(view::showItems));
    }

    @Override
    public void onDropView() {
        subscriptionList.unsubscribe();
        subscriptionList = null;
    }

    public interface View {

        void showItems(List<ItemModel> itemModels);

        Observable<Void> addItemClicks();
    }
}
