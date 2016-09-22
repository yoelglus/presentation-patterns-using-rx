package net.skyscanner.cleanarchitecture.presentation.model;

public class AddItemViewModel {

    private boolean mAddButtonEnabled;
    private boolean mShouldDismiss;

    public AddItemViewModel(boolean addButtonEnabled, boolean shouldDismiss) {
        mAddButtonEnabled = addButtonEnabled;
        mShouldDismiss = shouldDismiss;
    }

    public boolean isAddButtonEnabled() {
        return mAddButtonEnabled;
    }

    public boolean shouldDismiss() {
        return mShouldDismiss;
    }
}
