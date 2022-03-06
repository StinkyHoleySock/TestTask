package com.example.testtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_login.et_password as et_password1
import kotlinx.android.synthetic.main.activity_register.et_email as et_email1
import kotlinx.android.synthetic.main.activity_register.et_surname as et_surname

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Переход на страницу регистрации
        tv_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        tv_forgot_password.setOnClickListener {
            // TODO: 05.03.2022
        }

        btn_login.setOnClickListener {
            logInRegisteredUser()
        }
    }

    // Проверка корректности данных
    private fun validateLoginDetails(): Boolean {
        return when {

            TextUtils.isEmpty(et_email.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }

            TextUtils.isEmpty(et_password.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }
            else -> true
        }
    }

    // Вход для зарегистрированного пользователя
    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {
            val email = et_email.editText?.text.toString().trim {it <= ' '}
            val password = et_password.editText?.text.toString().trim {it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Firestore().getUserDetails(this)
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    //
    fun userLoggedInSuccess(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}