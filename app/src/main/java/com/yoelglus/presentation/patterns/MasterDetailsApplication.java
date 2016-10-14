package com.yoelglus.presentation.patterns;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.memoizrlabs.Shank;
import com.memoizrlabs.functions.Func0;
import com.memoizrlabs.functions.Func2;
import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;
import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.inject.RepositoryFactory;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.mvc.MvcAddItemController;
import com.yoelglus.presentation.patterns.mvc.MvcAddItemModel;
import com.yoelglus.presentation.patterns.mvc.MvcItemDetailsController;
import com.yoelglus.presentation.patterns.mvc.MvcItemDetailsModel;
import com.yoelglus.presentation.patterns.mvc.MvcItemsListController;
import com.yoelglus.presentation.patterns.mvc.MvcItemsListModel;
import com.yoelglus.presentation.patterns.mvp.MvpAddItemPresenter;
import com.yoelglus.presentation.patterns.mvp.MvpItemDetailsPresenter;
import com.yoelglus.presentation.patterns.mvp.MvpItemsListPresenter;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveAddItemPresenter;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveItemDetailsPresenter;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveItemsListPresenter;
import com.yoelglus.presentation.patterns.mvppassiverx.MvpPassiveRxAddItemPresenter;
import com.yoelglus.presentation.patterns.mvppassiverx.MvpPassiveRxItemDetailsPresenter;
import com.yoelglus.presentation.patterns.mvppassiverx.MvpPassiveRxItemsListPresenter;
import com.yoelglus.presentation.patterns.mvpvm.MvpVmAddItemPresenter;
import com.yoelglus.presentation.patterns.mvpvm.MvpVmItemDetailsPresenter;
import com.yoelglus.presentation.patterns.mvpvm.MvpVmItemsListPresenter;
import com.yoelglus.presentation.patterns.navigator.AppCompatActivityNavigator;
import com.yoelglus.presentation.patterns.navigator.Navigator;
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

        Shank.registerFactory(ItemsRepository.class, (Func0<ItemsRepository>) RepositoryFactory::createItemsRepo);
        Shank.registerNamedFactory(Scheduler.class, "io", (Func0<Scheduler>) Schedulers::io);
        Shank.registerNamedFactory(Scheduler.class, "main", (Func0<Scheduler>) AndroidSchedulers::mainThread);

        Shank.registerFactory(GetItems.class,
                (Func0<GetItems>) () -> new GetItems(Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class),
                        Shank.provideSingleton(ItemsRepository.class)));

        Shank.registerFactory(Navigator.class, new Func2<AppCompatActivity, Boolean, Navigator>() {
            @Override
            public Navigator call(AppCompatActivity activity, Boolean twoPane) {
                return new AppCompatActivityNavigator(activity, twoPane);
            }
        });


        Shank.registerFactory(GetItem.class,
                (Func0<GetItem>) () -> new GetItem(Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class),
                        Shank.provideSingleton(ItemsRepository.class)));

        Shank.registerFactory(AddItem.class,
                (Func0<AddItem>) () -> new AddItem(Shank.named("io").provideSingleton(Scheduler.class),
                        Shank.named("main").provideSingleton(Scheduler.class),
                        Shank.provideSingleton(ItemsRepository.class)));

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
                (Func0<MvpPassiveItemDetailsPresenter>) () -> new MvpPassiveItemDetailsPresenter(Shank.provideNew(
                        GetItem.class)));

        Shank.registerFactory(MvpPassiveAddItemPresenter.class,
                (Func0<MvpPassiveAddItemPresenter>) () -> new MvpPassiveAddItemPresenter(Shank.provideNew(AddItem.class)));

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
                (Func0<MvpPassiveRxItemDetailsPresenter>) () -> new MvpPassiveRxItemDetailsPresenter(Shank.provideNew(
                        GetItem.class)));

        Shank.registerFactory(MvpPassiveRxAddItemPresenter.class,
                (Func0<MvpPassiveRxAddItemPresenter>) () -> new MvpPassiveRxAddItemPresenter(Shank.provideNew(AddItem.class)));

        // MVP

        Shank.registerFactory(MvpItemsListPresenter.class,
                new Func2<AppCompatActivity, Boolean, MvpItemsListPresenter>() {
                    @Override
                    public MvpItemsListPresenter call(AppCompatActivity activity, Boolean twoPane) {
                        return new MvpItemsListPresenter(Shank.provideNew(GetItems.class),
                                new ItemModelsMapper(),
                                Shank.provideNew(Navigator.class, activity, twoPane));
                    }
                });

        Shank.registerFactory(MvpItemDetailsPresenter.class,
                (Func0<MvpItemDetailsPresenter>) () -> new MvpItemDetailsPresenter(Shank.provideNew(GetItem.class)));

        Shank.registerFactory(MvpAddItemPresenter.class,
                (Func0<MvpAddItemPresenter>) () -> new MvpAddItemPresenter(Shank.provideNew(AddItem.class)));

        // MVPVM

        Shank.registerFactory(MvpVmItemsListPresenter.class,
                new Func2<AppCompatActivity, Boolean, MvpVmItemsListPresenter>() {
                    @Override
                    public MvpVmItemsListPresenter call(AppCompatActivity activity, Boolean twoPane) {
                        return new MvpVmItemsListPresenter(Shank.provideNew(GetItems.class),
                                new ItemModelsMapper(),
                                Shank.provideNew(Navigator.class, activity, twoPane));
                    }
                });

        Shank.registerFactory(MvpVmItemDetailsPresenter.class,
                (Func0<MvpVmItemDetailsPresenter>) () -> new MvpVmItemDetailsPresenter(Shank.provideNew(GetItem.class)));

        Shank.registerFactory(MvpVmAddItemPresenter.class,
                (Func0<MvpVmAddItemPresenter>) () -> new MvpVmAddItemPresenter(Shank.provideNew(AddItem.class)));

        // MVPVM

        Shank.registerFactory(ItemsListViewModel.class, new Func2<AppCompatActivity, Boolean, ItemsListViewModel>() {
            @Override
            public ItemsListViewModel call(AppCompatActivity activity, Boolean twoPane) {
                return new ItemsListViewModel(Shank.provideNew(GetItems.class),
                        new ItemModelsMapper(),
                        Shank.provideNew(Navigator.class, activity, twoPane));
            }
        });

        Shank.registerFactory(ItemDetailViewModel.class,
                (Func0<ItemDetailViewModel>) () -> new ItemDetailViewModel(Shank.provideNew(GetItem.class)));

        Shank.registerFactory(AddItemViewModel.class,
                (Func0<AddItemViewModel>) () -> new AddItemViewModel(Shank.provideNew(AddItem.class)));

        // MVC
        Shank.registerFactory(MvcItemsListModel.class, (Func0<MvcItemsListModel>) MvcItemsListModel::new);

        Shank.registerFactory(MvcItemsListController.class,
                new Func2<AppCompatActivity, Boolean, MvcItemsListController>() {
                    @Override
                    public MvcItemsListController call(AppCompatActivity activity, Boolean twoPane) {
                        return new MvcItemsListController(Shank.provideNew(GetItems.class),
                                Shank.provideNew(Navigator.class, activity, twoPane),
                                Shank.provideSingleton(MvcItemsListModel.class));
                    }
                });

        Shank.registerFactory(MvcItemDetailsModel.class, (Func0<MvcItemDetailsModel>) MvcItemDetailsModel::new);

        Shank.registerFactory(MvcItemDetailsController.class,
                (Func0<MvcItemDetailsController>) () -> new MvcItemDetailsController(Shank.provideNew(GetItem.class),
                        Shank.provideSingleton(MvcItemDetailsModel.class)));

        Shank.registerFactory(MvcAddItemModel.class, (Func0<MvcAddItemModel>) MvcAddItemModel::new);

        Shank.registerFactory(MvcAddItemController.class,
                new Func2<AppCompatActivity, Boolean, MvcAddItemController>() {
                    @Override
                    public MvcAddItemController call(AppCompatActivity activity, Boolean twoPane) {
                        return new MvcAddItemController(Shank.provideNew(AddItem.class),
                                Shank.provideNew(Navigator.class, activity, twoPane),
                                Shank.provideSingleton(MvcAddItemModel.class));
                    }
                });
    }

}
