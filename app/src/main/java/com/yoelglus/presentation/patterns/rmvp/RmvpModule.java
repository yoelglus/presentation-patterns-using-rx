package com.yoelglus.presentation.patterns.rmvp;

import com.memoizrlabs.ShankModule;
import com.yoelglus.presentation.patterns.navigator.RmvpAppCompatActivityNavigator;

import android.support.v7.app.AppCompatActivity;

import static com.memoizrlabs.Shank.provideNew;
import static com.memoizrlabs.Shank.registerFactory;
import static com.yoelglus.presentation.patterns.AppModule.getItemRepository;

public class RmvpModule implements ShankModule {

    @Override
    public void registerFactories() {
        registerFactory(ItemsListPresenter.class, (AppCompatActivity activity) ->
                new ItemsListPresenter(getItemRepository(),
                        provideNew(RmvpAppCompatActivityNavigator.class, activity)));

        registerFactory(AddItemPresenter.class, (AppCompatActivity activity) ->
                new AddItemPresenter(getItemRepository(),
                        provideNew(RmvpAppCompatActivityNavigator.class, activity)));
    }
}
