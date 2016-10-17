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
        registerFactory(RmvpItemsListPresenter.class, (AppCompatActivity activity) ->
                new RmvpItemsListPresenter(getItemRepository(),
                        provideNew(RmvpAppCompatActivityNavigator.class, activity)));

        registerFactory(RmvpAddItemPresenter.class, (AppCompatActivity activity) ->
                new RmvpAddItemPresenter(getItemRepository(),
                        provideNew(RmvpAppCompatActivityNavigator.class, activity)));
    }
}
