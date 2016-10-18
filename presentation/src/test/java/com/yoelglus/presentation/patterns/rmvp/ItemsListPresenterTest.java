package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import org.junit.Before;
import org.junit.Test;

import rx.subjects.PublishSubject;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

public class ItemsListPresenterTest {

    private ItemsRepository itemsRepository = mock(ItemsRepository.class);
    private Navigator navigator = mock(Navigator.class);
    private ItemsListPresenter.View view = mock(ItemsListPresenter.View.class);

    private PublishSubject<Void> addItemSubject = PublishSubject.create();

    private ItemsListPresenter presenter = new ItemsListPresenter(itemsRepository, navigator);

    private Item item = new Item("id", "content", "detail");

    @Before
    public void setUp() {
        when(itemsRepository.getItems()).thenReturn(just(singletonList(item)));
        when(view.addItemClicks()).thenReturn(addItemSubject);

        presenter.takeView(view);
    }

    @Test
    public void should_show_items() {
        verify(view).showItems(singletonList(ItemModel.from(item)));
    }

    @Test
    public void should_navigate_to_add_item() {
        addItemSubject.onNext(null);

        verify(navigator).navigateToAddItem();
    }
}