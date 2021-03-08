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
import com.gaurav.testrg.adapter.TaskAdapter
import com.gaurav.testrg.app.BaseActivity
import com.gaurav.testrg.custom.BoldTextView
import com.gaurav.testrg.custom.RegularButton
import com.gaurav.testrg.database.DatabaseHelper
import com.gaurav.testrg.databinding.ActivityTaskListBinding
import com.gaurav.testrg.databinding.DialogAddTaskBinding
import com.gaurav.testrg.model.Task
import com.gaurav.testrg.model.Users
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskListActivity : BaseActivity() {
    private lateinit var binding: ActivityTaskListBinding
    lateinit var dialogOption: AlertDialog
    private var taskList: ArrayList<Task>? = null
    private var taskAdapter:TaskAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var title = findViewById<BoldTextView>(R.id.appTitle)
        var logout = findViewById<RegularButton>(R.id.logout)
        title.text = sharedPreference.getUser()?.nama
        logout.setOnClickListener {
            sharedPreference.clearSharedPreference()
            startNewActivity(LoginActivity())
            finish()
        }
        binding.addTask.setOnClickListener {
            addTaskDialog()
        }

        setRecyclerview(DatabaseHelper.getTaskAllData() as ArrayList<Users>)

    }

    fun setRecyclerview(taskList:ArrayList<Users>){
        Log.d(TAG,taskList.size.toString())

        if(taskList.size>0){
            binding.noData.visibility = View.GONE
            binding.constraintLayout.visibility = View.VISIBLE

            taskAdapter = TaskAdapter(taskList as ArrayList<Task>)
            var viewManager = LinearLayoutManager(this)
            binding.taskListRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = taskAdapter
            }

        }else{
            binding.noData.visibility = View.VISIBLE
            binding.constraintLayout.visibility = View.GONE
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun addTaskDialog(){

        var materialAlertDialogBuilder = MaterialAlertDialogBuilder(this, R.style.PauseDialog)
        var customAlertView = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_task,
                null,
                false
        )
        val bindingDialog : DialogAddTaskBinding=DialogAddTaskBinding.bind(customAlertView)

        materialAlertDialogBuilder.setView(customAlertView)
        materialAlertDialogBuilder.background = getDrawable(R.drawable.card_view)
        bindingDialog.save.setOnClickListener(View.OnClickListener {

            bindingDialog.textInputLayoutTitle.isErrorEnabled = false
            bindingDialog.textInputDescription.isErrorEnabled = false
            when {
                bindingDialog.title.isEmpty() -> {
                    (bindingDialog.textInputLayoutTitle).run {
                        isErrorEnabled = true;error =
                        getString(R.string.please_enter_name);requestFocus()
                    }
                }
                bindingDialog.description.isEmpty() -> {
                    (bindingDialog.textInputDescription).run {
                        isErrorEnabled = true;error =
                        getString(R.string.please_enter_password);requestFocus()
                    }
                }
                else -> {
                    dialogOption.dismiss()
                    var task = Task()
                    task.title = bindingDialog.title.text.toString().trim()
                    task.description = bindingDialog.description.text.toString().trim()
                    var value = DatabaseHelper.insertTaskData(task)

                    setRecyclerview(DatabaseHelper.getTaskAllData() as ArrayList<Users>)

                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(getString(R.string.success))
                            .setContentText(getString(R.string.task_created))
                            .setConfirmClickListener(object :
                                SweetAlertDialog.OnSweetClickListener {
                                override fun onClick(sDialog: SweetAlertDialog) {
                                    sDialog.dismiss()
                                }
                            })
                            .show()

                }

            }
        })
        bindingDialog.close.setOnClickListener {
            dialogOption.dismiss()
        }

        dialogOption = materialAlertDialogBuilder.show()
        dialogOption.setCanceledOnTouchOutside(false)
    }
}