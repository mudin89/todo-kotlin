package com.najmuddin.todo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.najmuddin.todo.MyApplication
import com.najmuddin.todo.R
import com.najmuddin.todo.viewmodel.MainViewModel
import javax.inject.Inject

class FormActivity : AppCompatActivity() {
    var context: FormActivity? = null

    @Inject
    lateinit var viewModel: MainViewModel

    var btnConfirm : LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        context = this
        btnConfirm = findViewById(R.id.saveButton)

        (application as MyApplication).getAppComponent().doInjection(this)
        viewModel = ViewModelProviders.of(context!!).get(MainViewModel::class.java)

        var mainTitle  =  findViewById<TextView>(R.id.toolbarTitle)
        var btnBack  =  findViewById<ImageView>(R.id.btnBack)
        mainTitle.text = "Add new To-Do List"


        btnConfirm?.setOnClickListener {
            doSave()
        }

        btnBack.visibility = View.VISIBLE
        btnBack.setOnClickListener {
            finish()
        }
    }

    fun doSave(){

    }



}