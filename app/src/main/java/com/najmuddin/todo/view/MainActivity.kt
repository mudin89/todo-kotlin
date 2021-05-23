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
import com.najmuddin.todo.utils.AppXBus
import com.najmuddin.todo.view.adapter.RecyclerViewAdapter
import com.najmuddin.todo.viewmodel.MainViewModel
import io.paperdb.Paper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var context: MainActivity? = null
    var recyclerView: RecyclerView? = null
    var recyclerViewAdapter: RecyclerViewAdapter? = null
    var btnAdd: ImageView? = null

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        Paper.init(this@MainActivity)

        (application as MyApplication).getAppComponent().doInjection(this)
        val mainTitle  =  findViewById<TextView>(R.id.toolbarTitle)

        mainTitle.text = "To-do List"

        recyclerView = findViewById(R.id.rcvMain)
        viewModel = ViewModelProviders.of(context!!).get(MainViewModel::class.java)
        viewModel.getUserMutableLiveData().observe(context!!, todoListUpdateObserver)

        btnAdd = findViewById(R.id.btnAdd)

        btnAdd?.setOnClickListener{
            navigationManager.toFormActivity(this@MainActivity, null)
        }

        //added listener for any update on live data
        val cbListener = updateCheckBoxListener()
        compositeDisposable.add(cbListener)
        val refreshListener = refreshListListener()
        compositeDisposable.add(refreshListener)
    }


    var todoListUpdateObserver: Observer<ArrayList<Todo>> = Observer<ArrayList<Todo>> { todoArrayList ->
        recyclerViewAdapter = RecyclerViewAdapter(context, todoArrayList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.adapter = recyclerViewAdapter
    }

    private fun updateCheckBoxListener(): Disposable {
        val d = AppXBus.listen(AppXBus.AppEvents.UpdateLatestTodoList::class.java)
                .subscribe({ position ->
                    updateCheckBox(position)
                }, { t: Throwable? -> })
        return d
    }

    private fun refreshListListener(): Disposable {
        val d = AppXBus.listen(AppXBus.AppEvents.refreshTodoList::class.java)
                .subscribe({ ok ->
                    refreshList()
                }, { t: Throwable? -> })
        return d
    }

    fun updateCheckBox(utlcb: AppXBus.AppEvents.UpdateLatestTodoList){
        //
    }

    fun refreshList() {
        viewModel.refreshTodoList()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}