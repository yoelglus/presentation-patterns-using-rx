package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.Observable;

class ItemsListPresenter extends AbstractPresenter<ItemsListPresenter.View> {

    private ItemsRepository itemsRepository;
    private Navigator navigator;

    ItemsListPresenter(ItemsRepository itemsRepository, Navigator navigator) {
        this.itemsRepository = itemsRepository;
        this.navigator = navigator;
    }

    @Override
    public void onTakeView(ItemsListPresenter.View view) {
        unsubscribeOnViewDropped(view.addItemClicks().subscribe(aVoid -> navigator.navigateToAddItem()));

        unsubscribeOnViewDropped(itemsRepository.getItems().map(ItemModelsMapper::map).subscribe(view::showItems));
    }

    public interface View {

        void showItems(List<ItemModel> itemModels);

        Observable<Void> addItemClicks();
    }
}
