package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.model.ItemModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

@RunWith(MockitoJUnitRunner.class)
public class ItemDetailViewModelTest {

    private static final String CONTENT_TEXT = "Content";
    private static final String DETAIL_TEXT = "detail";
    private static final String ITEM_ID = "123";

    private ItemsRepository itemsRepository = Mockito.mock(ItemsRepository.class);

    private ItemDetailViewModel itemDetailViewModel = new ItemDetailViewModel(itemsRepository, ITEM_ID);

    @Before
    public void setUp() {
        when(itemsRepository.getItem(anyString()))
                .thenReturn(just(new Item(ITEM_ID, CONTENT_TEXT, DETAIL_TEXT)));
    }

    @Test
    public void should_emit_item_model_from_repository() {
        TestSubscriber<ItemModel> testSubscriber = TestSubscriber.create();
        itemDetailViewModel.itemModel().subscribe(testSubscriber);

        itemDetailViewModel.onStart();

        testSubscriber.assertValue(ItemModel.from(new Item(ITEM_ID, CONTENT_TEXT, DETAIL_TEXT)));
    }
}

