package net.skyscanner.cleanarchitecture.presentation.viewmodel;

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
import rx.subjects.PublishSubject;

public class ItemsListViewModel extends AbstractViewModel {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private PublishSubject<List<ItemModel>> mItemModelsSubject = PublishSubject.create();
    private Action1<Void> mAddItemClicks = new Action1<Void>() {
        @Override
        public void call(Void aVoid) {
            mNavigator.navigateToAddItem();
        }
    };
    private Action1<String> mItemClicks = new Action1<String>() {
        @Override
        public void call(String id) {
            mNavigator.navigateToItem(id);
        }
    };
    private Subscription mGetItemsSubscription;

    public ItemsListViewModel(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemsSubscription = mGetItems.execute(new Subscriber<List<Item>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Item> items) {
                mItemModelsSubject.onNext(mItemModelsMapper.map(items));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mGetItemsSubscription.unsubscribe();
    }

    public Observable<List<ItemModel>> itemModels() {
        return mItemModelsSubject;
    }

    public Action1<Void> addItemClicks() {
        return mAddItemClicks;
    }

    public Action1<String> itemClicks() {
        return mItemClicks;
    }

}
