package com.najmuddin.todo.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ads.uts.utils.NavigationManager
import com.najmuddin.todo.MyApplication
import com.najmuddin.todo.R
import com.najmuddin.todo.model.Todo
import com.najmuddin.todo.view.adapter.RecyclerViewAdapter
import com.najmuddin.todo.viewmodel.MainViewModel
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    var context: MainActivity? = null
    var recyclerView: RecyclerView? = null
    var recyclerViewAdapter: RecyclerViewAdapter? = null
    var btnAdd: ImageView? = null

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var navigationManager: NavigationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        (application as MyApplication).getAppComponent().doInjection(this)
        val mainTitle  =  findViewById<TextView>(R.id.toolbarTitle)

        mainTitle.text = "To-do List"

        recyclerView = findViewById(R.id.rcvMain)
        viewModel = ViewModelProviders.of(context!!).get(MainViewModel::class.java)
        viewModel.getUserMutableLiveData().observe(context!!, todoListUpdateObserver)

        btnAdd = findViewById(R.id.btnAdd)

        btnAdd?.setOnClickListener{
            navigationManager.toFormActivity(this@MainActivity,null)
        }
    }


    var todoListUpdateObserver: Observer<ArrayList<Todo>> = Observer<ArrayList<Todo>> { todoArrayList ->
        recyclerViewAdapter = RecyclerViewAdapter(context, todoArrayList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.adapter = recyclerViewAdapter
    }
}