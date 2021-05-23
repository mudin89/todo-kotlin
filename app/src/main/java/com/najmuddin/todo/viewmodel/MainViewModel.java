package com.najmuddin.todo.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.najmuddin.todo.model.Todo;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    MutableLiveData<ArrayList<Todo>> todoLiveData;
    ArrayList<Todo> todoArrayList;

    @Inject
    public MainViewModel() {
        todoLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Todo>> getUserMutableLiveData() {
        return todoLiveData;
    }

    public void init(){
        populateList();
        todoLiveData.setValue(todoArrayList);
    }

    public void populateList(){
        todoArrayList = new ArrayList<>();
        Todo todo = new Todo();
        todo.setTitle("Automated Testing Script");
        Todo todo2 = new Todo();
        todo2.setTitle("Automated Testing Script 2");
        Todo todo3 = new Todo();
        todo3.setTitle("Automated Testing Script 3");
        Todo todo4 = new Todo();
        todo4.setTitle("Automated Testing Script 4");
        Todo todo5 = new Todo();
        todo5.setTitle("Automated Testing Script 5");


        todoArrayList.add(todo);
        todoArrayList.add(todo2);
        todoArrayList.add(todo3);
        todoArrayList.add(todo4);
        todoArrayList.add(todo5);
        todoArrayList.add(todo);
    }
}
