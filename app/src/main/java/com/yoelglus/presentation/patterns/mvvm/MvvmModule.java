package com.yoelglus.presentation.patterns.mvvm;

import com.memoizrlabs.ShankModule;
import com.yoelglus.presentation.patterns.navigator.MvvmAppCompatActivityNavigator;

import android.support.v7.app.AppCompatActivity;

import static com.memoizrlabs.Shank.provideNew;
import static com.memoizrlabs.Shank.registerFactory;
import static com.yoelglus.presentation.patterns.AppModule.getItemRepository;

public class MvvmModule implements ShankModule {

    @Override
    public void registerFactories() {
        registerFactory(ItemsListViewModel.class, (AppCompatActivity activity) ->
                new ItemsListViewModel(getItemRepository(),
                        provideNew(MvvmAppCompatActivityNavigator.class, activity)));

        registerFactory(ItemDetailViewModel.class, (String id) -> new ItemDetailViewModel(getItemRepository(), id));

        registerFactory(AddItemViewModel.class, (AppCompatActivity activity) ->
                new AddItemViewModel(getItemRepository(), provideNew(MvvmAppCompatActivityNavigator.class, activity)));
    }
}
