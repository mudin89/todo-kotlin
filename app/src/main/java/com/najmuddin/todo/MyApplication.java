package com.najmuddin.todo;

import android.app.Application;
import android.content.Context;

import com.najmuddin.todo.di.AppComponent;
import com.najmuddin.todo.di.DaggerAppComponent;
import com.najmuddin.todo.di.UtilsModule;


public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
