package com.yoelglus.presentation.patterns;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.memoizrlabs.Shank;
import com.memoizrlabs.functions.Func0;
import com.memoizrlabs.functions.Func1;
import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.rmvp.RmvpAddItemPresenter;
import com.yoelglus.presentation.patterns.rmvp.RmvpItemDetailsPresenter;
import com.yoelglus.presentation.patterns.rmvp.RmvpItemsListPresenter;
import com.yoelglus.presentation.patterns.navigator.MvvmAppCompatActivityNavigator;
import com.yoelglus.presentation.patterns.navigator.Navigator;
import com.yoelglus.presentation.patterns.navigator.ReactiveMvpAppCompatActivityNavigator;
import com.yoelglus.presentation.patterns.viewmodel.AddItemViewModel;
import com.yoelglus.presentation.patterns.viewmodel.ItemDetailViewModel;
import com.yoelglus.presentation.patterns.viewmodel.ItemsListViewModel;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MasterDetailsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Shank.registerFactory(ItemsRepository.class, (Func0<ItemsRepository>) ItemsRepository::new);
        Shank.registerNamedFactory(Scheduler.class, "io", (Func0<Scheduler>) Schedulers::io);
        Shank.registerNamedFactory(Scheduler.class, "main", (Func0<Scheduler>) AndroidSchedulers::mainThread);

        Shank.registerNamedFactory(Navigator.class, "rmvp", new Func1<AppCompatActivity, Navigator>() {
            @Override
            public Navigator call(AppCompatActivity activity) {
                return new ReactiveMvpAppCompatActivityNavigator(activity);
            }
        });

        Shank.registerNamedFactory(Navigator.class, "mvvm", new Func1<AppCompatActivity, Navigator>() {
            @Override
            public Navigator call(AppCompatActivity activity) {
                return new MvvmAppCompatActivityNavigator(activity);
            }
        });

        // MVP Passive Rx

        Shank.registerFactory(RmvpItemsListPresenter.class,
                new Func1<AppCompatActivity, RmvpItemsListPresenter>() {
                    @Override
                    public RmvpItemsListPresenter call(AppCompatActivity activity) {
                        return new RmvpItemsListPresenter(Shank.provideNew(ItemsRepository.class),
                                Shank.named("rmvp").provideNew(Navigator.class, activity));
                    }
                });

        Shank.registerFactory(RmvpItemDetailsPresenter.class,
                new Func1<String, RmvpItemDetailsPresenter>() {
                    @Override
                    public RmvpItemDetailsPresenter call(String id) {
                        return new RmvpItemDetailsPresenter(Shank.provideNew(ItemsRepository.class), id);
                    }
                });

        Shank.registerFactory(RmvpAddItemPresenter.class,
                (Func0<RmvpAddItemPresenter>) () -> new RmvpAddItemPresenter(Shank.provideNew(
                        ItemsRepository.class)));

        // MVVM

        Shank.registerFactory(ItemsListViewModel.class, new Func1<AppCompatActivity, ItemsListViewModel>() {
            @Override
            public ItemsListViewModel call(AppCompatActivity activity) {
                return new ItemsListViewModel(Shank.provideNew(ItemsRepository.class),
                        Shank.named("mvvm").provideNew(Navigator.class, activity));
            }
        });

        Shank.registerFactory(ItemDetailViewModel.class, new Func1<String, ItemDetailViewModel>() {
            @Override
            public ItemDetailViewModel call(String id) {
                return new ItemDetailViewModel(Shank.provideNew(ItemsRepository.class), id);
            }
        });

        Shank.registerFactory(AddItemViewModel.class,
                (Func0<AddItemViewModel>) () -> new AddItemViewModel(Shank.provideNew(ItemsRepository.class)));

    }

}
