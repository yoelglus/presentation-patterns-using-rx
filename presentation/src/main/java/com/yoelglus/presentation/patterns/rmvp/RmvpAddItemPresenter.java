package com.yoelglus.presentation.patterns.rmvp;


import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import rx.Observable;

public class RmvpAddItemPresenter extends AbstractPresenter<RmvpAddItemPresenter.View> {

    private ItemsRepository itemsRepository;
    private Navigator navigator;

    public RmvpAddItemPresenter(ItemsRepository itemsRepository, Navigator navigator) {
        this.itemsRepository = itemsRepository;
        this.navigator = navigator;
    }

    @Override
    protected void onTakeView() {

        Observable<ItemToAdd> addEnabled = Observable.combineLatest(view.contentTextChanged(),
                view.detailTextChanged(),
                ItemToAdd::new).doOnNext(itemToAdd -> view.setAddButtonEnabled(itemToAdd.valid()));

        unsubscribeOnViewDropped(view.addButtonClicks()
                .withLatestFrom(addEnabled, (aVoid, itemToAdd) -> itemToAdd)
                .subscribe(this::addItem));

        unsubscribeOnViewDropped(view.cancelButtonClicks().subscribe(aVoid -> navigator.closeCurrentScreen()));
    }

    private void addItem(ItemToAdd itemToAdd) {
        itemsRepository.addItem(itemToAdd.content, itemToAdd.details)
                .subscribe(s -> navigator.closeCurrentScreen());
    }

    private static class ItemToAdd {

        private String content;
        private String details;

        private ItemToAdd(String content, String details) {
            this.content = content;
            this.details = details;
        }

        private boolean valid() {
            return content.length() > 0 && details.length() > 0;
        }
    }

    public interface View {

        void setAddButtonEnabled(boolean enabled);

        Observable<String> contentTextChanged();

        Observable<String> detailTextChanged();

        Observable<Void> addButtonClicks();

        Observable<Void> cancelButtonClicks();
    }
}
