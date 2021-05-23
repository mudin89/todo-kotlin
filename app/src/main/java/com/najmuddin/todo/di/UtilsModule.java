package com.najmuddin.todo.di;

import com.ads.uts.utils.NavigationManager;
import com.ads.uts.utils.PaperDbManager;
import com.najmuddin.todo.viewmodel.MainViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


@Module
public class UtilsModule {

    @Provides
    @Singleton
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    MainViewModel getViewModelFactory() {
        return new MainViewModel();
    }

    @Provides
    @Singleton
    NavigationManager getNavigationManager() {
        return new NavigationManager();
    }
}
