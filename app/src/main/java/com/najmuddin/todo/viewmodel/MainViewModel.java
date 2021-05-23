package com.najmuddin.todo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ads.uts.utils.PaperDbManager;
import com.najmuddin.todo.model.Todo;
import com.najmuddin.todo.utils.AppXBus;

import java.util.ArrayList;
import java.util.List;

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
//        populateList();
        ArrayList<Todo> storeList = PaperDbManager.TODO.INSTANCE.readTodoList();

        if(storeList == null){
            storeList = new ArrayList<>();
        }

        todoArrayList = storeList;
        todoLiveData.setValue(todoArrayList);
    }

    public Todo getTodoByPosition(int index){
        if(todoArrayList != null && !(index >= todoArrayList.size())){
            return todoArrayList.get(index);
        }

        return null;
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

    public void addNewTodo(Todo todo){
        todoArrayList.add(todo);
        todoLiveData.postValue(todoArrayList);
        PaperDbManager.TODO.INSTANCE.saveTodoList(todoArrayList);
        AppXBus.INSTANCE.publish(new AppXBus.AppEvents.refreshTodoList());
    }

    public void updateTodo(int position, Todo todo){
        todoArrayList.set(position,todo);
        todoLiveData.postValue(todoArrayList);
        PaperDbManager.TODO.INSTANCE.saveTodoList(todoArrayList);
        AppXBus.INSTANCE.publish(new AppXBus.AppEvents.refreshTodoList());
    }


    public void refreshTodoList(){
        todoArrayList =  PaperDbManager.TODO.INSTANCE.readTodoList();
        todoLiveData.postValue(todoArrayList);
    }

    public void updateCheckBoxTodo(int position){
//        todoArrayList.set(position);
    }
}
