package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.observers.TestSubscriber;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.observers.TestSubscriber.create;

@RunWith(MockitoJUnitRunner.class)
public class AddItemViewModelTest {

    private static final String EMPTY = "";
    private static final String CONTENT_TEXT = "Content";
    private static final String DETAIL_TEXT = "detail";
    private static final String NEW_ITEM_ID = "123";
    private AddItemViewModel addItemViewModel;
    private PublishSubject<String> itemSubject = PublishSubject.create();

    @Mock
    private Navigator navigator;
    @Mock
    private ItemsRepository itemsRepository;

    private TestSubscriber<Boolean> addButtonEnabledSubscriber;

    @Before
    public void setUp() throws Exception {
        when(itemsRepository.addItem(CONTENT_TEXT,
                DETAIL_TEXT)).thenReturn(itemSubject);

        addItemViewModel = new AddItemViewModel(itemsRepository, navigator);
        addButtonEnabledSubscriber = create();
        addItemViewModel.addButtonEnabled().subscribe(addButtonEnabledSubscriber);
        addItemViewModel.onStart();
        addButtonEnabledSubscriber.assertValuesAndClear(false);
    }

    @Test
    public void should_dismiss_when_cancel_was_clicked() throws Exception {
        addItemViewModel.cancelClicked();

        verify(navigator).closeCurrentScreen();
    }

    @Test
    public void should_disable_add_button_when_content_is_empty() throws Exception {
        addItemViewModel.contentTextChanged(EMPTY);

        addButtonEnabledSubscriber.assertValue(false);
    }

    @Test
    public void should_disable_add_button_when_detail_is_empty() throws Exception {
        addItemViewModel.detailTextChanged(EMPTY);

        addButtonEnabledSubscriber.assertValue(false);
    }

    @Test
    public void should_enable_add_button_when_content_and_detail_are_not_empty() throws Exception {
        addItemViewModel.contentTextChanged(CONTENT_TEXT);
        addButtonEnabledSubscriber.assertValuesAndClear(false);
        addItemViewModel.detailTextChanged(DETAIL_TEXT);

        addButtonEnabledSubscriber.assertValues(true);
    }

    @Test
    public void should_call_repository_when_add_button_clicked() throws Exception {
        addItemViewModel.contentTextChanged(CONTENT_TEXT);
        addItemViewModel.detailTextChanged(DETAIL_TEXT);

        addItemViewModel.addItemClicked();

        verify(itemsRepository).addItem(CONTENT_TEXT, DETAIL_TEXT);
    }

    @Test
    public void should_close_current_screen_when_item_added() throws Exception {
        givenAddItem();

        itemSubject.onNext(NEW_ITEM_ID);

        verify(navigator).closeCurrentScreen();
    }

    @Test
    public void should_unsubscribe_onStop() throws Exception {
        givenAddItem();

        addItemViewModel.onStop();
        itemSubject.onNext(NEW_ITEM_ID);

        verify(navigator, never()).closeCurrentScreen();
    }

    private void givenAddItem() {
        addItemViewModel.contentTextChanged(CONTENT_TEXT);
        addItemViewModel.detailTextChanged(DETAIL_TEXT);
        addItemViewModel.addItemClicked();
    }
}