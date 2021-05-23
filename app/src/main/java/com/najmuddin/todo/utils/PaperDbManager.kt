package com.ads.uts.utils

import com.najmuddin.todo.model.Todo
import io.paperdb.Paper

class PaperDbManager {

    object CUBE {
        val TODO_LIST = "TODO_LIST"
        val TODO_OBJECT = "TODO_OBJECT"

        fun saveTodo(list : Todo){
            Paper.book().write(TODO_LIST, list)
        }

        fun readTodo() : Todo{
            return Paper.book().read(TODO_LIST, Todo())
        }

        fun saveTodoList(list : ArrayList<Todo>){
            Paper.book().write(TODO_OBJECT, list)

        }

        fun readTodoList() : ArrayList<Todo>?{
            return Paper.book().read(TODO_OBJECT, null)
        }

        //remove here
        fun removeAllList() {
            Paper.book().delete(TODO_LIST)
            Paper.book().delete(TODO_OBJECT)
        }
    }

}