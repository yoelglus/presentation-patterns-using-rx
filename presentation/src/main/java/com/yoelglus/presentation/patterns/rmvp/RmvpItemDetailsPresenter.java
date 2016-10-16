package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Subscription;

public class RmvpItemDetailsPresenter extends AbstractPresenter<RmvpItemDetailsPresenter.View> {

    private ItemsRepository itemsRepository;
    private Subscription getItemSubscription;
    private String itemId;

    public RmvpItemDetailsPresenter(ItemsRepository itemsRepository, String itemId) {
        this.itemsRepository = itemsRepository;
        this.itemId = itemId;
    }


    @Override
    public void onTakeView() {
        getItemSubscription = itemsRepository.getItem(itemId)
                .map(ItemModel::from)
                .subscribe(view::showItem);
    }

    @Override
    public void onDropView() {
        getItemSubscription.unsubscribe();
    }

    public interface View {

        void showItem(ItemModel itemModel);
    }
}
