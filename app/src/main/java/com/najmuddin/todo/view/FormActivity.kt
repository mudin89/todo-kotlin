package com.najmuddin.todo.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.najmuddin.todo.MyApplication
import com.najmuddin.todo.R
import com.najmuddin.todo.model.Todo
import com.najmuddin.todo.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class FormActivity : AppCompatActivity() {
    var context: FormActivity? = null

    @Inject
    lateinit var viewModel: MainViewModel

    var btnConfirm : LinearLayout? = null
    lateinit var etTitle : TextView
    lateinit var btnStartDate : TextView
    lateinit var btnEndDate : TextView
    lateinit var mainTitle : TextView
    lateinit var btnBack : ImageView
    lateinit var tvConfirm : TextView
    var id : Int? = null

    var startDate :Date? = null
    var endDate :Date? = null
    var isCompleted = false
    var sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        context = this
        btnConfirm = findViewById(R.id.saveButton)

        (application as MyApplication).getAppComponent().doInjection(this)
        viewModel.refreshTodoList()

        //initView
        mainTitle  =  findViewById(R.id.toolbarTitle)
        btnBack  =  findViewById(R.id.btnBack)
        etTitle  =  findViewById(R.id.etTitle)
        btnStartDate  =  findViewById(R.id.tvStartDate)
        btnEndDate  =  findViewById(R.id.tvEndDate)
        tvConfirm = findViewById(R.id.tvConfirm)
        initView()

        viewModel.userMutableLiveData.observe(this@FormActivity,{
            // Update the UI when the data has changed
        })

        //check if any id is passed? passed means update, none means creating new one
        val key = getId()
        if(key != null){
            id  = key.toIntOrNull()
            if(id  != null){
                val todo = viewModel.getTodoByPosition(id!!)
                if(todo != null){
                    fillInTheData(todo)
                }
            }
        }

    }

    fun getId() : String? {
        var data: String? = null
        if (intent != null && intent.extras != null) {
            val bundle = intent.extras
            if (bundle?.getString("id") != null) {
                data = bundle.getString("id")
            }
        }

        return data
    }

    //fill in the view with data
    fun fillInTheData(todo: Todo){
        btnStartDate.text = todo.getStartDateAsString()
        btnEndDate.text = todo.getEndDateAsString()
        etTitle.text = todo.title

        startDate = todo.startDate ?: Calendar.getInstance().time
        endDate = todo.endDate ?: Calendar.getInstance().time

        isCompleted = todo.isComplete
        tvConfirm.text = "Save Todo"
    }


    fun initView(){
        mainTitle.text = "Add new To-Do List"

        //clicklistener init at here

        btnConfirm?.setOnClickListener {
            //button will proceed only if the variable needed is fulfilled
            //checker if any infomation is null
            if(startDate ==null || endDate == null){
                Toast.makeText(
                    applicationContext,
                    "Please input start/end date",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if(etTitle.text == ""){
                etTitle.setError("Please insert title to the todo list")
                return@setOnClickListener
            }

            var todo = Todo()

            todo.startDate = startDate
            todo.endDate =endDate
            todo.title = etTitle.text.toString()
            todo.isComplete = isCompleted

            if(id != null){
                doSave(id!!,todo)
            } else {
                createNew(todo)
            }

        }

        btnBack.visibility = View.VISIBLE
        btnBack.setOnClickListener {
            finish()
        }

        btnStartDate.setOnClickListener {
            showDateDialog(true)
        }

        btnEndDate.setOnClickListener {
            showDateDialog(false)
        }
    }

    fun showDateDialog(isStartDate: Boolean){
        //using dialog picker for date selecter
        // Get Current Date
        val c = getCalendar(if(isStartDate) startDate else endDate)
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, monthOfYear, dayOfMonth)

                if (isStartDate) {
                    startDate = calendar.time
                    btnStartDate.text = sdf.format(calendar.time)
                } else {
                    endDate = calendar.time
                    btnEndDate.text = sdf.format(calendar.time)
                }
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    fun getCalendar(date: Date?): Calendar {
        //convert calendar to date
        val cal = Calendar.getInstance()
        if(date != null){
            cal.time = date
        }

        return cal
    }

    fun doSave(position: Int,todo: Todo){ //save old obejct
        viewModel.updateTodo(position,todo)
        finish()
    }

    fun createNew(todo: Todo){ //create new object
        viewModel.addNewTodo(todo)
        finish()
    }



}