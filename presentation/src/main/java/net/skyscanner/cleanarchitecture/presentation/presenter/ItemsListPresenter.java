package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;
import net.skyscanner.cleanarchitecture.presentation.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class ItemsListPresenter extends AbstractPresenter<ItemsListPresenter.View> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Subscription mGetItemsSubscription;
    private Navigator mNavigator;

    public ItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    @Override
    void onTakeView() {
        mView.addItemClicks().subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mNavigator.navigateToAddItem();
            }
        });

        mView.itemClicks().subscribe(new Action1<String>() {
            @Override
            public void call(String id) {
                mNavigator.navigateToItem(id);
            }
        });

        mGetItemsSubscription = mGetItems.execute(new Subscriber<List<Item>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Item> items) {
                mView.showItems(mItemModelsMapper.map(items));
            }
        });
    }

    @Override
    void onDropView() {
        if (!mGetItemsSubscription.isUnsubscribed()) {
            mGetItemsSubscription.unsubscribe();
        }
    }

    public interface View {
        void showItems(List<ItemModel> itemModel);
        Observable<Void> addItemClicks();
        Observable<String> itemClicks();
    }
}
