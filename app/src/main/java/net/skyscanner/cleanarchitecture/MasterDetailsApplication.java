package net.skyscanner.cleanarchitecture;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.memoizrlabs.Shank;
import com.memoizrlabs.functions.Func0;
import com.memoizrlabs.functions.Func1;
import com.memoizrlabs.functions.Func2;

import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;
import net.skyscanner.cleanarchitecture.domain.usecases.AddItem;
import net.skyscanner.cleanarchitecture.domain.usecases.GetItem;
import net.skyscanner.cleanarchitecture.domain.usecases.GetItems;
import net.skyscanner.cleanarchitecture.inject.RepositoryFactory;
import net.skyscanner.cleanarchitecture.navigator.AppCompatActivityNavigator;
import net.skyscanner.cleanarchitecture.presentation.mapper.ItemModelsMapper;
import net.skyscanner.cleanarchitecture.presentation.navigator.Navigator;
import net.skyscanner.cleanarchitecture.presentation.viewmodel.AddItemViewModel;
import net.skyscanner.cleanarchitecture.presentation.viewmodel.ItemDetailViewModel;
import net.skyscanner.cleanarchitecture.presentation.viewmodel.ItemsListViewModel;

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

        Shank.registerFactory(ItemsListViewModel.class, new Func2<AppCompatActivity, Boolean, ItemsListViewModel>() {
            @Override
            public ItemsListViewModel call(AppCompatActivity activity, Boolean twoPane) {
                return new ItemsListViewModel(Shank.provideNew(GetItems.class),
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

        Shank.registerFactory(ItemDetailViewModel.class, new Func1<String, ItemDetailViewModel>() {
            @Override
            public ItemDetailViewModel call(String id) {
                return new ItemDetailViewModel(Shank.provideNew(GetItem.class, id));
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

        Shank.registerFactory(AddItemViewModel.class, new Func0<AddItemViewModel>() {
            @Override
            public AddItemViewModel call() {
                return new AddItemViewModel(Shank.provideNew(AddItem.class));
            }
        });
    }
}
