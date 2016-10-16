package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicBoolean;

import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.Observable.just;
import static rx.observers.TestSubscriber.create;

@RunWith(MockitoJUnitRunner.class)
public class AddItemViewModelTest {

    private static final String EMPTY = "";
    private static final String CONTENT_TEXT = "Content";
    private static final String DETAIL_TEXT = "detail";
    private static final String NEW_ITEM_ID = "123";
    private AddItemViewModel addItemViewModel;

    @Mock
    private Navigator navigator;
    @Mock
    private ItemsRepository itemsRepository;

    private AtomicBoolean addItemTriggered = new AtomicBoolean(false);

    private TestScheduler ioScheduler = new TestScheduler();
    private TestScheduler mainScheduler = new TestScheduler();
    private TestSubscriber<Boolean> addButtonEnabledSubscriber;

    @Before
    public void setUp() throws Exception {
        when(itemsRepository.addItem(CONTENT_TEXT,
                DETAIL_TEXT)).thenReturn(just(NEW_ITEM_ID).doOnNext(s -> addItemTriggered.set(true)));

        addItemViewModel = new AddItemViewModel(itemsRepository, navigator, ioScheduler, mainScheduler);
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
    public void should_subscribe_repository_on_io_scheduler() throws Exception {
        givenAddItem();

        assertFalse(addItemTriggered.get());

        ioScheduler.triggerActions();

        assertTrue(addItemTriggered.get());
    }

    @Test
    public void should_observe_repository_on_main_thread() throws Exception {
        givenAddItem();

        verify(navigator, never()).closeCurrentScreen();
        ioScheduler.triggerActions();
        verify(navigator, never()).closeCurrentScreen();
        mainScheduler.triggerActions();

        verify(navigator).closeCurrentScreen();
    }

    @Test
    public void should_unsubscribe_onStop() throws Exception {
        givenAddItem();
        addItemViewModel.onStop();
        ioScheduler.triggerActions();
        mainScheduler.triggerActions();

        verify(navigator, never()).closeCurrentScreen();
    }

    private void givenAddItem() {
        addItemViewModel.contentTextChanged(CONTENT_TEXT);
        addItemViewModel.detailTextChanged(DETAIL_TEXT);
        addItemViewModel.addItemClicked();
    }
}