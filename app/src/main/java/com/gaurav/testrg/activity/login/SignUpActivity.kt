package com.gaurav.testrg.activity.login

import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog
import com.gaurav.testrg.R
import com.gaurav.testrg.app.BaseActivity
import com.gaurav.testrg.database.DatabaseHelper
import com.gaurav.testrg.databinding.ActivitySignupBinding
import com.gaurav.testrg.model.Users

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper.initDatabaseInstance(this)

        binding.alreadyAMember.setOnClickListener {
            startNewActivity(LoginActivity())
            finish()
        }
        binding.signup.setOnClickListener {

            binding.textInputLayoutName.isErrorEnabled = false
            binding.textInputLayoutEmail.isErrorEnabled = false
            binding.textInputPassword.isErrorEnabled = false
            when{
                binding.name.isEmpty() -> {
                    (binding.textInputLayoutName).run { isErrorEnabled = true ;error = getString(R.string.please_enter_name);requestFocus() }
                }
                !binding.email.isEmailValidOrNot() -> {
                    (binding.textInputLayoutEmail).run { isErrorEnabled = true ;error = getString(R.string.please_enter_email);requestFocus() }
                }
                binding.password.isEmpty() -> {
                    (binding.textInputPassword).run { isErrorEnabled = true ;error = getString(R.string.please_enter_password);requestFocus() }
                }
                else -> {
                    var user = DatabaseHelper.getUserData(binding.email.text.toString().trim())
                    //Log.d(TAG, DatabaseHelper.getUserData(binding.email.text.toString().trim()).toString())
                    if(user.id==0){
                        var user = Users()
                        user.nama = binding.name.text.toString().trim()
                        user.email = binding.email.text.toString().trim()
                        user.password = binding.password.text.toString().trim()

                        val stat = DatabaseHelper.insertUserData(user)
                        if (stat > 0) {
                            //Log.d(TAG, stat.toString())
                        }
                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(getString(R.string.success))
                            .setContentText("Signup successfully.")
                            .setConfirmClickListener(object :
                                SweetAlertDialog.OnSweetClickListener {
                                override fun onClick(sDialog: SweetAlertDialog) {
                                    sDialog.dismiss()
                                    finish()
                                }
                            })
                            .show()

                    }else{
                        showError(getString(R.string.oops),"User Already register with this email.")
                    }

                }

            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}