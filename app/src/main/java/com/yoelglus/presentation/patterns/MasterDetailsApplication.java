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

        Shank.registerFactory(MvpItemDetailsPresenter.class, new Func1<String, MvpItemDetailsPresenter>() {
            @Override
            public MvpItemDetailsPresenter call(String id) {
                return new MvpItemDetailsPresenter(Shank.provideNew(GetItem.class, id));
            }
        });

        Shank.registerFactory(MvpAddItemPresenter.class, new Func0<MvpAddItemPresenter>() {
            @Override
            public MvpAddItemPresenter call() {
                return new MvpAddItemPresenter(Shank.provideNew(AddItem.class));
            }
        });

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

        Shank.registerFactory(MvpVmItemDetailsPresenter.class, new Func1<String, MvpVmItemDetailsPresenter>() {
            @Override
            public MvpVmItemDetailsPresenter call(String id) {
                return new MvpVmItemDetailsPresenter(Shank.provideNew(GetItem.class, id));
            }
        });

        Shank.registerFactory(MvpVmAddItemPresenter.class, new Func0<MvpVmAddItemPresenter>() {
            @Override
            public MvpVmAddItemPresenter call() {
                return new MvpVmAddItemPresenter(Shank.provideNew(AddItem.class));
            }
        });

        // MVPVM

        Shank.registerFactory(ItemsListViewModel.class, new Func2<AppCompatActivity, Boolean, ItemsListViewModel>() {
            @Override
            public ItemsListViewModel call(AppCompatActivity activity, Boolean twoPane) {
                return new ItemsListViewModel(Shank.provideNew(GetItems.class),
                        new ItemModelsMapper(),
                        Shank.provideNew(Navigator.class, activity, twoPane));
            }
        });

        Shank.registerFactory(ItemDetailViewModel.class, new Func1<String, ItemDetailViewModel>() {
            @Override
            public ItemDetailViewModel call(String id) {
                return new ItemDetailViewModel(Shank.provideNew(GetItem.class, id));
            }
        });

        Shank.registerFactory(AddItemViewModel.class, new Func0<AddItemViewModel>() {
            @Override
            public AddItemViewModel call() {
                return new AddItemViewModel(Shank.provideNew(AddItem.class));
            }
        });

        // MVC
        Shank.registerFactory(MvcItemsListModel.class, new Func0<MvcItemsListModel>() {
            @Override
            public MvcItemsListModel call() {
                return new MvcItemsListModel();
            }
        });

        Shank.registerFactory(MvcItemsListController.class,
                new Func2<AppCompatActivity, Boolean, MvcItemsListController>() {
                    @Override
                    public MvcItemsListController call(AppCompatActivity activity, Boolean twoPane) {
                        return new MvcItemsListController(Shank.provideNew(GetItems.class),
                                Shank.provideNew(Navigator.class, activity, twoPane),
                                Shank.provideSingleton(MvcItemsListModel.class));
                    }
                });

        Shank.registerFactory(MvcItemDetailsModel.class, new Func0<MvcItemDetailsModel>() {
            @Override
            public MvcItemDetailsModel call() {
                return new MvcItemDetailsModel();
            }
        });

        Shank.registerFactory(MvcItemDetailsController.class, new Func1<String, MvcItemDetailsController>() {
            @Override
            public MvcItemDetailsController call(String id) {
                return new MvcItemDetailsController(Shank.provideNew(GetItem.class, id),
                        Shank.provideSingleton(MvcItemDetailsModel.class));
            }
        });

        Shank.registerFactory(MvcAddItemModel.class, new Func0<MvcAddItemModel>() {
            @Override
            public MvcAddItemModel call() {
                return new MvcAddItemModel();
            }
        });

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
