package net.skyscanner.cleanarchitecture;

import android.app.Application;

import com.memoizrlabs.Shank;
import com.memoizrlabs.functions.Func0;

import net.skyscanner.cleanarchitecture.data.DummyItemsRepository;
import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;
import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.presenter.ItemsListPresenter;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MasterDetailsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Shank.registerFactory(ItemsRepository.class, new Func0<ItemsRepository>() {
            @Override
            public ItemsRepository call() {
                return new DummyItemsRepository();
            }
        });
        Shank.registerNamedFactory(Scheduler.class, "io", new Func0<Scheduler>() {
            @Override
            public Scheduler call() {
                return Schedulers.io();
            }
        });
        Shank.registerNamedFactory(Scheduler.class, "main", new Func0<Scheduler>() {
            @Override
            public Scheduler call() {
                return AndroidSchedulers.mainThread();
            }
        });

        Shank.registerFactory(GetItems.class, new Func0<GetItems>() {
            @Override
            public GetItems call() {
                return new GetItems(Shank.provideSingleton(ItemsRepository.class),
                        Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class));
            }
        });

        Shank.registerFactory(ItemsListPresenter.class, new Func0<ItemsListPresenter>() {
            @Override
            public ItemsListPresenter call() {
                return new ItemsListPresenter(Shank.provideNew(GetItems.class), new ItemModelsMapper());
            }
        });
    }
}
