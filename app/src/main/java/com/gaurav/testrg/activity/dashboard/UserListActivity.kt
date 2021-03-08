package com.gaurav.testrg.activity.dashboard

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.gaurav.testrg.R
import com.gaurav.testrg.activity.login.LoginActivity
import com.gaurav.testrg.adapter.UserAdapter
import com.gaurav.testrg.app.BaseActivity
import com.gaurav.testrg.custom.BoldTextView
import com.gaurav.testrg.custom.RegularButton
import com.gaurav.testrg.database.DatabaseHelper
import com.gaurav.testrg.databinding.ActivityUserListBinding
import com.gaurav.testrg.databinding.DialogAddUserBinding
import com.gaurav.testrg.model.Users
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UserListActivity : BaseActivity() {
    private lateinit var bindingMain: ActivityUserListBinding
    lateinit var dialogOption: AlertDialog
    private var userList: ArrayList<Users>? = null
    var userAdapter: UserAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)


        var title = findViewById<BoldTextView>(R.id.appTitle)
        var logout = findViewById<RegularButton>(R.id.logout)
        title.text = "Admin"
        logout.setOnClickListener {
            sharedPreference.clearSharedPreference()
            startNewActivity(LoginActivity())
            finish()
        }
        bindingMain.addTask.setOnClickListener {
            addUserDialog()
        }



        setRecyclerview(DatabaseHelper.getUserAllData() as ArrayList<Users>)



    }

    fun setRecyclerview(userList:ArrayList<Users>){
        Log.d(TAG,userList.size.toString())

        if(userList.size>0){
            bindingMain.noData.visibility = View.GONE
            bindingMain.constraintLayout.visibility = View.VISIBLE

            userAdapter = UserAdapter(userList as ArrayList<Users>)
            var viewManager = LinearLayoutManager(this)
            bindingMain.userListRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = userAdapter
            }

        }else{
            bindingMain.noData.visibility = View.VISIBLE
            bindingMain.constraintLayout.visibility = View.GONE
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun addUserDialog() {

        var materialAlertDialogBuilder = MaterialAlertDialogBuilder(this, R.style.PauseDialog)
        var customAlertView = LayoutInflater.from(this).inflate(
            R.layout.dialog_add_user,
            null,
            false
        )
        val binding: DialogAddUserBinding = DialogAddUserBinding.bind(customAlertView)


        materialAlertDialogBuilder.setView(customAlertView)
        materialAlertDialogBuilder.background = getDrawable(R.drawable.card_view)
        binding.create.setOnClickListener(View.OnClickListener {
            binding.textInputLayoutName.isErrorEnabled = false
            binding.textInputLayoutEmail.isErrorEnabled = false
            binding.textInputPassword.isErrorEnabled = false
            when {
                binding.name.isEmpty() -> {
                    (binding.textInputLayoutName).run {
                        isErrorEnabled = true;error =
                        getString(R.string.please_enter_name);requestFocus()
                    }
                }
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
                    var user = DatabaseHelper.getUserData(binding.email.text.toString().trim())
                    //Log.d(TAG, DatabaseHelper.getUserData(binding.email.text.toString().trim()).toString())
                    if (user.id == 0) {

                        dialogOption.dismiss()
                        var user = Users()
                        user.nama = binding.name.text.toString().trim()
                        user.email = binding.email.text.toString().trim()
                        user.password = binding.password.text.toString().trim()

                        val stat = DatabaseHelper.insertUserData(user)
                        if (stat > 0) {
                            //Log.d(TAG, stat.toString())
                        }
                        /*userAdapter = UserAdapter(DatabaseHelper.getUserAllData() as ArrayList<Users>)
                        userAdapter!!.notifyDataSetChanged()*/
                        setRecyclerview(DatabaseHelper.getUserAllData() as ArrayList<Users>)
                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(getString(R.string.success))
                            .setContentText(getString(R.string.user_created))
                            .setConfirmClickListener(object :
                                SweetAlertDialog.OnSweetClickListener {
                                override fun onClick(sDialog: SweetAlertDialog) {

                                    sDialog.dismiss()
                                }
                            })
                            .show()

                    } else {
                        showError(
                            getString(R.string.oops),
                            "User Already register with this email."
                        )
                    }

                }

            }
        })
        binding.close.setOnClickListener {
            dialogOption.dismiss()
        }

        dialogOption = materialAlertDialogBuilder.show()
        dialogOption.setCanceledOnTouchOutside(false)
    }
}