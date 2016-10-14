package com.yoelglus.presentation.patterns.mvppassive;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.Observable;
import rx.Subscription;

public class MvpPassiveItemDetailsPresenter extends AbstractPresenter<MvpPassiveItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public MvpPassiveItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    public void onTakeView() {
        mGetItemSubscription = mView.loadItem()
                .flatMap(mGetItem::execute)
                .map(ItemModel::from)
                .subscribe(mView::showItem);
    }

    @Override
    public void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
        void showItem(ItemModel itemModel);

        Observable<String> loadItem();
    }
}
