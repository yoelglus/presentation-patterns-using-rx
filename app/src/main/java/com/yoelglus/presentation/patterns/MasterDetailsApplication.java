package com.yoelglus.presentation.patterns;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.memoizrlabs.Shank;
import com.memoizrlabs.functions.Func0;
import com.memoizrlabs.functions.Func1;
import com.memoizrlabs.functions.Func2;
import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;
import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.inject.RepositoryFactory;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveAddItemPresenter;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveItemDetailsPresenter;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveItemsListPresenter;
import com.yoelglus.presentation.patterns.mvppassiverx.MvpPassiveRxAddItemPresenter;
import com.yoelglus.presentation.patterns.mvppassiverx.MvpPassiveRxItemDetailsPresenter;
import com.yoelglus.presentation.patterns.mvppassiverx.MvpPassiveRxItemsListPresenter;
import com.yoelglus.presentation.patterns.navigator.AppCompatActivityNavigator;
import com.yoelglus.presentation.patterns.navigator.Navigator;

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
                return RepositoryFactory.createItemsRepo();
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
                return new GetItems(Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class),
                        Shank.provideSingleton(ItemsRepository.class));
            }
        });

        Shank.registerFactory(Navigator.class, new Func2<AppCompatActivity, Boolean, Navigator>() {
            @Override
            public Navigator call(AppCompatActivity activity, Boolean twoPane) {
                return new AppCompatActivityNavigator(activity, twoPane);
            }
        });


        Shank.registerFactory(GetItem.class, new Func1<String, GetItem>() {
            @Override
            public GetItem call(String id) {
                return new GetItem(Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class),
                        Shank.provideSingleton(ItemsRepository.class),
                        id);
            }
        });

        Shank.registerFactory(AddItem.class, new Func0<AddItem>() {
            @Override
            public AddItem call() {
                return new AddItem(Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class),
                        Shank.provideSingleton(ItemsRepository.class));
            }
        });

        // MVP Passive

        Shank.registerFactory(MvpPassiveItemsListPresenter.class,
                new Func2<AppCompatActivity, Boolean, MvpPassiveItemsListPresenter>() {
                    @Override
                    public MvpPassiveItemsListPresenter call(AppCompatActivity activity, Boolean twoPane) {
                        return new MvpPassiveItemsListPresenter(Shank.provideNew(GetItems.class),
                                new ItemModelsMapper(),
                                Shank.provideNew(Navigator.class, activity, twoPane));
                    }
                });

        Shank.registerFactory(MvpPassiveItemDetailsPresenter.class,
                new Func1<String, MvpPassiveItemDetailsPresenter>() {
                    @Override
                    public MvpPassiveItemDetailsPresenter call(String id) {
                        return new MvpPassiveItemDetailsPresenter(Shank.provideNew(GetItem.class, id));
                    }
                });

        Shank.registerFactory(MvpPassiveAddItemPresenter.class, new Func0<MvpPassiveAddItemPresenter>() {
            @Override
            public MvpPassiveAddItemPresenter call() {
                return new MvpPassiveAddItemPresenter(Shank.provideNew(AddItem.class));
            }
        });

        // MVP Passive Rx

        Shank.registerFactory(MvpPassiveRxItemsListPresenter.class,
                new Func2<AppCompatActivity, Boolean, MvpPassiveRxItemsListPresenter>() {
                    @Override
                    public MvpPassiveRxItemsListPresenter call(AppCompatActivity activity, Boolean twoPane) {
                        return new MvpPassiveRxItemsListPresenter(Shank.provideNew(GetItems.class),
                                new ItemModelsMapper(),
                                Shank.provideNew(Navigator.class, activity, twoPane));
                    }
                });

        Shank.registerFactory(MvpPassiveRxItemDetailsPresenter.class,
                new Func1<String, MvpPassiveRxItemDetailsPresenter>() {
                    @Override
                    public MvpPassiveRxItemDetailsPresenter call(String id) {
                        return new MvpPassiveRxItemDetailsPresenter(Shank.provideNew(GetItem.class, id));
                    }
                });

        Shank.registerFactory(MvpPassiveRxAddItemPresenter.class, new Func0<MvpPassiveRxAddItemPresenter>() {
            @Override
            public MvpPassiveRxAddItemPresenter call() {
                return new MvpPassiveRxAddItemPresenter(Shank.provideNew(AddItem.class));
            }
        });

    }
}
