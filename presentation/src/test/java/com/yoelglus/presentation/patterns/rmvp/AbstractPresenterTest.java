package com.yoelglus.presentation.patterns.rmvp;

import org.junit.Test;
import org.mockito.Mockito;

import rx.Observable;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AbstractPresenterTest {
    static class Presenter extends AbstractPresenter<Presenter.View> {
        @Override
        protected void onTakeView() {
            unsubscribeOnViewDropped(view.events().subscribe(v -> view.action()));
        }

        @Override
        protected void onDropView() {
        }

        interface View {
            Observable<Void> events();
            void action();
        }
    }

    private Presenter presenter = new Presenter();
    private Presenter.View view = mock(Presenter.View.class);
    private PublishSubject<Void> subject = PublishSubject.create();

    @Test
    public void should_unsubscribe_when_view_is_dropped() {
        Mockito.when(view.events()).thenReturn(subject);
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

    private void verifyNumberOfResultingActions(int wantedNumberOfInvocations) {
        verify(view, times(wantedNumberOfInvocations)).action();
    }

    private void triggerEvent() {
        subject.onNext(null);
    }
}