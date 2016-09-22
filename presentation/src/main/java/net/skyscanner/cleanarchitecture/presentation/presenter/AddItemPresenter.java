package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.AddItem;
import net.skyscanner.cleanarchitecture.presentation.model.AddItemViewModel;

import rx.Subscriber;

public class AddItemPresenter extends AbstractPresenter<AddItemViewModel> {

    private AddItem mAddItem;
    private String mContentText = "";
    private String mDetailText = "";

    public AddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    @Override
    protected void onSubscribe() {
        super.onSubscribe();
        setAddButtonEnableState();
    }

    private void setAddButtonEnableState() {
        notifyOnChange(new AddItemViewModel(mContentText.length() > 0 && mDetailText.length() > 0, false));
    }

    public void onContentTextChanged(String contentText) {
        mContentText = contentText;
        setAddButtonEnableState();
    }

    public void onDetailTextChanged(String detailText) {
        mDetailText = detailText;
        setAddButtonEnableState();
    }

    public void onAddButtonClicked() {
        mAddItem.execute(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String id) {
                notifyOnChange(new AddItemViewModel(true, true));
            }
        }, mContentText, mDetailText);
    }

    public void onCancelButtonClicked() {
        notifyOnChange(new AddItemViewModel(true, true));
    }
}
