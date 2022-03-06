package com.example.testtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.core.view.get
import androidx.core.view.isEmpty
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Переход на страницу входа
        tv_btn_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        // Обработка нажатия кнопки регитсрации
        // с проверкой корректности введённых данных
        btn_register.setOnClickListener {
            registerUser()
        }

    }

    // Проверка ввода данных
    private fun validateRegisterDetails(): Boolean {
        return when {

            TextUtils.isEmpty(et_name.toString().trim{it<=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }

            TextUtils.isEmpty(et_surname.toString().trim{it<=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }

            TextUtils.isEmpty(et_email.toString().trim{it<=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }

            TextUtils.isEmpty(et_password.toString().trim{it<=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.toString().trim{it<=' '}) -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }

            // Добавить проверку на совпадение паролей 
            // TODO: 04.03.2022
            
            !cb_accept.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.invalidate_data), true)
                false
            }

            else -> {
                true
            }
        }
    }

    private fun registerUser() {
        // Если введённые данные корректны
        if (validateRegisterDetails()) {

            val email: String = et_email.editText?.text.toString().trim { it <= ' ' }
            val password: String = et_password.editText?.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // Если регитсрация удалась
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                et_name.editText?.text.toString().trim { it <= ' '},
                                et_surname.editText?.text.toString().trim { it <= ' '},
                                et_email.editText?.text.toString().trim { it <= ' '}
                            )

                            Firestore().registerUser(user)

                            FirebaseAuth.getInstance().signOut()
                            finish()

                            showErrorSnackBar(resources.getString(R.string.success_registery),
                            false
                            )

                            FirebaseAuth.getInstance().signOut()
                            finish()

                        } else {
                        // Уведомление об ошибке, если регистрация не удалась
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
                )
        }
    }




}