package com.gaurav.testrg.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.gaurav.testrg.R
import com.gaurav.testrg.activity.dashboard.TaskListActivity
import com.gaurav.testrg.activity.dashboard.UserListActivity
import com.gaurav.testrg.app.BaseActivity
import com.gaurav.testrg.app.Constant
import com.gaurav.testrg.database.DatabaseHelper
import com.gaurav.testrg.databinding.ActivityLoginBinding
import com.gaurav.testrg.model.Users

class LoginActivity : BaseActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "user data ${sharedPreference.getValueBoolean(Constant.IS_LOGIN,false)}")
        if(sharedPreference.getValueBoolean(Constant.IS_LOGIN,false)){
            startNewActivity(TaskListActivity())
            finish()
        }else if(sharedPreference.getValueBoolean(Constant.IS_ADMIN,false)){
            startNewActivity(UserListActivity())
            finish()
        }

        binding.notAMember.setOnClickListener {
            startNewActivity(SignUpActivity())
        }

        var userList = DatabaseHelper.getUserAllData()
        for (user in userList) {
            Log.d(TAG, "user data ${user.email} : ${user.password}")
        }
        binding.login.setOnClickListener {
            binding.textInputLayoutEmail.isErrorEnabled = false
            binding.textInputPassword.isErrorEnabled = false
            when {
                !binding.email.isEmailValidOrNot() -> {
                    (binding.textInputLayoutEmail).run {
                        isErrorEnabled = true;error =
                        getString(R.string.please_enter_email);requestFocus()
                    }
                }
                binding.password.isEmpty() -> {
                    (binding.textInputPassword).run {
                        isErrorEnabled = true;error =
                        getString(R.string.please_enter_password);requestFocus()
                    }
                }
                else -> {
                    var user = DatabaseHelper.getUserData(
                        binding.email.text.toString(),
                        binding.password.text.toString()
                    )
                    if(binding.email.text.toString().trim().equals("admin@gmail.com",true) && binding.password.text.toString().trim().equals("admin",false) ){
                        sharedPreference.save(Constant.IS_ADMIN, true)
                        sharedPreference.save(Constant.USER_NAME, "Admin")
                        startNewActivity(UserListActivity())
                        finish()
                    }
                    else if (user.id != 0) {
                        sharedPreference.save(Constant.IS_LOGIN, true)
                        sharedPreference.save(Constant.USER_EMAIL, user.email)
                        startNewActivity(TaskListActivity())
                        finish()
                        //showSuccessr(getString(R.string.success), "Login successfully.")

                    } else {
                        showError(getString(R.string.oops), "Invalid email or password.")
                    }
                    Log.d(TAG, user.email)
                }
            }

        }
    }
}