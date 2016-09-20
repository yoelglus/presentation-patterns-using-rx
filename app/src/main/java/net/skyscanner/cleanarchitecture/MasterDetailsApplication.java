package net.skyscanner.cleanarchitecture;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.memoizrlabs.Shank;
import com.memoizrlabs.functions.Func0;
import com.memoizrlabs.functions.Func1;
import com.memoizrlabs.functions.Func2;

import net.skyscanner.cleanarchitecture.data.DummyItemsRepository;
import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;
import net.skyscanner.cleanarchitecture.domain.usecases.AddItem;
import net.skyscanner.cleanarchitecture.domain.usecases.GetItem;
import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.navigator.AppCompatActivityNavigator;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.navigator.Navigator;
import net.skyscanner.cleanarchitecture.presentation.presenter.ItemDetailsPresenter;
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

        Shank.registerFactory(ItemsListPresenter.class, new Func2<AppCompatActivity, Boolean, ItemsListPresenter>() {
            @Override
            public ItemsListPresenter call(AppCompatActivity activity, Boolean twoPane) {
                return new ItemsListPresenter(Shank.provideNew(GetItems.class),
                        new ItemModelsMapper(),
                        Shank.provideNew(Navigator.class, activity, twoPane));
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

        Shank.registerFactory(ItemDetailsPresenter.class, new Func1<String, ItemDetailsPresenter>() {
            @Override
            public ItemDetailsPresenter call(String id) {
                return new ItemDetailsPresenter(Shank.provideNew(GetItem.class, id));
            }
        });

        Shank.registerFactory(AddItem.class, new Func2<String, String, AddItem>() {
            @Override
            public AddItem call(String content, String details) {
                return new AddItem(Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class),
                        Shank.provideSingleton(ItemsRepository.class),
                        content,
                        details);
            }
        });

    }
}
