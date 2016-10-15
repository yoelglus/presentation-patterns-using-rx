package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddItemViewModelTest {

    private AddItemViewModel addItemViewModel;

    @Mock
    private Navigator navigator;
    @Mock
    private ItemsRepository itemsRepository;

    private TestScheduler ioScheduler = new TestScheduler();
    private TestScheduler mainScheduler = new TestScheduler();

    @Before
    public void setUp() throws Exception {
        addItemViewModel = new AddItemViewModel(itemsRepository, navigator, ioScheduler, mainScheduler);
    }

    @Test
    public void should_dismiss_when_cancel_was_clicked() throws Exception {
        addItemViewModel.cancelClicked();

        verify(navigator).closeCurrentScreen();
    }
}