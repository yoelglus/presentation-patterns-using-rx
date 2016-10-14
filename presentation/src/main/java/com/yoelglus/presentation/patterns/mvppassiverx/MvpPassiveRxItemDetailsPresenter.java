package com.yoelglus.presentation.patterns.mvppassiverx;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class MvpPassiveRxItemDetailsPresenter extends AbstractPresenter<MvpPassiveRxItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public MvpPassiveRxItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    public void onTakeView() {
        mGetItemSubscription = mView.loadItem()
                .flatMap(mGetItem::execute)
                .map(ItemModel::from)
                .subscribe(mView.showItem());
    }

    @Override
    public void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
        Action1<ItemModel> showItem();

        Observable<String> loadItem();
    }
}
