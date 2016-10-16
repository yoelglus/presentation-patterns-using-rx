package com.yoelglus.presentation.patterns;

import com.yoelglus.presentation.patterns.mvvm.MvvmModule;
import com.yoelglus.presentation.patterns.rmvp.RmvpModule;

import android.app.Application;

import static com.memoizrlabs.ShankModuleInitializer.initializeModules;

public class MasterDetailsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeModules(new AppModule(), new RmvpModule(), new MvvmModule());
    }
}
