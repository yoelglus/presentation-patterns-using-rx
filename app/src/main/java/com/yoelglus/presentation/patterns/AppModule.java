package com.yoelglus.presentation.patterns;

import com.memoizrlabs.ShankModule;
import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.navigator.MvvmAppCompatActivityNavigator;
import com.yoelglus.presentation.patterns.navigator.RmvpAppCompatActivityNavigator;

import static com.memoizrlabs.Shank.provideSingleton;
import static com.memoizrlabs.Shank.registerFactory;

public class AppModule implements ShankModule {

    @Override
    public void registerFactories() {
        registerFactory(ItemsRepository.class, ItemsRepository::new);
        registerFactory(RmvpAppCompatActivityNavigator.class, RmvpAppCompatActivityNavigator::new);
        registerFactory(MvvmAppCompatActivityNavigator.class, MvvmAppCompatActivityNavigator::new);
    }

    public static ItemsRepository getItemRepository() {
        return provideSingleton(ItemsRepository.class);
    }
}
