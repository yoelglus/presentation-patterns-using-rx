package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import org.junit.Before;
import org.junit.Test;

import rx.subjects.PublishSubject;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddItemPresenterTest {

    private ItemsRepository repository = mock(ItemsRepository.class);
    private Navigator navigator = mock(Navigator.class);
    private AddItemPresenter.View view = mock(AddItemPresenter.View.class);

    private AddItemPresenter presenter = new AddItemPresenter(repository, navigator);
    private PublishSubject<String> contentSubject = PublishSubject.create();
    private PublishSubject<String> detailsSubject = PublishSubject.create();
    private PublishSubject<Void> addButtonSubject = PublishSubject.create();
    private PublishSubject<Void> cancelButtonSubject = PublishSubject.create();

    private PublishSubject<String> addItemSubject = PublishSubject.create();

    @Before
    public void setUp() {
        when(view.contentTextChanged()).thenReturn(contentSubject);
        when(view.detailTextChanged()).thenReturn(detailsSubject);
        when(view.addButtonClicks()).thenReturn(addButtonSubject);
        when(view.cancelButtonClicks()).thenReturn(cancelButtonSubject);
        when(repository.addItem(anyString(), anyString())).thenReturn(addItemSubject);
        presenter.takeView(view);
    }

    @Test
    public void should_show_add_enabled_when_content_and_detail_text_become_not_empty() {
        verify(view, never()).setAddButtonEnabled(anyBoolean());

        detailsSubject.onNext("detail");

        verify(view, never()).setAddButtonEnabled(anyBoolean());

        contentSubject.onNext("content");

        verify(view).setAddButtonEnabled(true);
    }

    @Test
    public void should_show_add_disabled_when_content_or_detail_text_become_not_empty() {
        detailsSubject.onNext("detail");
        contentSubject.onNext("content");

        verify(view).setAddButtonEnabled(true);

        detailsSubject.onNext("");

        verify(view).setAddButtonEnabled(false);
    }

    @Test
    public void should_add_item_to_repository_when_adding_item() {
        detailsSubject.onNext("a detail");
        contentSubject.onNext("some content");

        addButtonSubject.onNext(null);

        verify(repository).addItem("some content", "a detail");
    }

    @Test
    public void should_close_current_screen_when_item_has_been_added() {
        detailsSubject.onNext("a detail");
        contentSubject.onNext("some content");

        addButtonSubject.onNext(null);

        verify(navigator, never()).closeCurrentScreen();

        addItemSubject.onNext(null);

        verify(navigator).closeCurrentScreen();
    }

    @Test
    public void should_close_current_screen_when_cancel() {
        cancelButtonSubject.onNext(null);

        verify(navigator).closeCurrentScreen();
    }
}