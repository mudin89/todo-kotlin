package com.ads.uts.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.najmuddin.todo.view.FormActivity

class NavigationManager(){

    fun toFormActivity(context: Context, id: String?){
        val intent = Intent(context, FormActivity::class.java)
        if(id != null){
            intent.putExtra("id", id)
        }

        context.startActivity(intent)
    }


}