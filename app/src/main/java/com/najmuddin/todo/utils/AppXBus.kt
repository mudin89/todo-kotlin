package com.najmuddin.todo.utils

import com.najmuddin.todo.model.Todo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

public object AppXBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)

    class AppEvents{

        class UpdateLatestTodoList(var todoList: ArrayList<Todo>){
            fun getList():ArrayList<Todo>{
                return todoList
            }
        }

        class refreshTodoList()
    }
}