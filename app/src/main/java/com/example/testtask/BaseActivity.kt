package com.example.testtask

import android.annotation.SuppressLint
import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    // Функция для показа snackBar
    @SuppressLint("ShowToast")
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBar.setBackgroundTint(
                ContextCompat.getColor(
                    this,
                    R.color.colorError
                )
            )
        } else {
            snackBar.setBackgroundTint(
                ContextCompat.getColor(
                    this,
                    R.color.colorSuccess
                )
            )
        }
        snackBar.show()
    }
}