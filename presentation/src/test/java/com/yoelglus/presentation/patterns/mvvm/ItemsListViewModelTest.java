package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Scheduler;
import rx.observers.TestSubscriber;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.Observable.just;
import static rx.schedulers.Schedulers.immediate;

public class ItemsListViewModelTest {

    private static final String CONTENT_TEXT = "Content";
    private static final String DETAIL_TEXT = "detail";
    private static final String ITEM_ID = "123";

    private Scheduler ioScheduler = immediate();
    private Scheduler mainScheduler = immediate();

    private ItemsRepository itemsRepository = mock(ItemsRepository.class);
    private Navigator navigator = mock(Navigator.class);

    private ItemsListViewModel itemsListViewModel =
            new ItemsListViewModel(itemsRepository, navigator, ioScheduler, mainScheduler);
    private Item item = new Item(ITEM_ID, CONTENT_TEXT, DETAIL_TEXT);
    private List<Item> items = singletonList(item);

    @Before
    public void setUp() {
        when(itemsRepository.getItems()).thenReturn(just(items));
    }

    @Test
    public void should_emit_item_models() {
        TestSubscriber<List<ItemModel>> testSubscriber = TestSubscriber.create();
        itemsListViewModel.itemModels().subscribe(testSubscriber);

        itemsListViewModel.onStart();

        testSubscriber.assertValue(singletonList(ItemModel.from(item)));
    }

    @Test
    public void should_navigateToAddItem() {
        itemsListViewModel.addItemClicked();

        verify(navigator).navigateToAddItem();
    }

    @Test
    public void should_navigateToItem() {
        itemsListViewModel.itemClicked("id");

        verify(navigator).navigateToItem("id");
    }
}