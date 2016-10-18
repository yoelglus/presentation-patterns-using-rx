package com.yoelglus.presentation.patterns.rmvp;

import org.junit.Test;

import rx.Observable;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractPresenterTest {

    static class Presenter extends AbstractPresenter<Presenter.View> {

        @Override
        protected void onTakeView(Presenter.View view) {
            unsubscribeOnViewDropped(view.events().subscribe(v -> view.action()));
        }

        @Override
        protected void onDropView(Presenter.View view) {
        }

        interface View {

            Observable<Void> events();

            void action();
        }
    }

    private Presenter presenter = new Presenter();
    private Presenter.View view = mock(Presenter.View.class);
    private PublishSubject<Void> eventSubject = PublishSubject.create();

    @Test
    public void should_unsubscribe_when_view_is_dropped() {
        when(view.events()).thenReturn(eventSubject);
        presenter.takeView(view);

        triggerEvent();
        verifyNumberOfResultingActions(1);
        triggerEvent();
        verifyNumberOfResultingActions(2);
        triggerEvent();
        verifyNumberOfResultingActions(3);

        presenter.dropView(view);

        triggerEvent();
        verifyNumberOfResultingActions(3);
        triggerEvent();
        verifyNumberOfResultingActions(3);
    }

    private void verifyNumberOfResultingActions(int numberOfActions) {
        verify(view, times(numberOfActions)).action();
    }

    private void triggerEvent() {
        eventSubject.onNext(null);
    }
}