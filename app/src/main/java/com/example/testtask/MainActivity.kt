package com.example.testtask

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Получение имени в профиле
        val sharedPreferences = getSharedPreferences(
            Constants.PREFERENCES,
            Context.MODE_PRIVATE
        )

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!

        tv_username.text = username

    }
}


