package com.najmuddin.todo.di;


import com.najmuddin.todo.view.FormActivity;
import com.najmuddin.todo.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MainActivity mainActivity);
    void doInjection(FormActivity formActivity);

}
