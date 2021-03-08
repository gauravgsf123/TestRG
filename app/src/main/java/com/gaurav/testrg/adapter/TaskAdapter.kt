package com.gaurav.testrg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.testrg.R
import com.gaurav.testrg.database.DatabaseHelper
import com.gaurav.testrg.databinding.TaskListItemBinding
import com.gaurav.testrg.model.Task
import com.gaurav.testrg.model.Users

class TaskAdapter(var taskList: ArrayList<Task>): RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    inner class MyViewHolder(var view : View) : RecyclerView.ViewHolder(view){
        val binding: TaskListItemBinding = TaskListItemBinding.bind(itemView)
        fun bindItems(i: Int) {
            var task = taskList[i]
            binding.title.text = task.title
            binding.description.text = task.description
            binding.etTitle.setText(task.title)
            binding.etDescription.setText(task.description)
            binding.delete.setOnClickListener{
                DatabaseHelper.deleteTaskData(taskList.get(i).id)
                taskList = DatabaseHelper.getTaskAllData() as ArrayList<Task>
                notifyDataSetChanged()
            }

            binding.edit.setOnClickListener {
                binding.constraintOne.visibility = View.GONE
                binding.constraintTwo.visibility = View.VISIBLE
            }
            binding.cancel.setOnClickListener {
                binding.constraintOne.visibility = View.VISIBLE
                binding.constraintTwo.visibility = View.GONE
            }

            binding.update.setOnClickListener {
                when{
                    binding.etTitle.text.toString().isEmpty() -> {
                        (binding.textInputLayoutTitle).run {
                            isErrorEnabled = true; error = "Please Enter Title";requestFocus()
                        }
                    }
                    binding.etDescription.text.toString().isEmpty() -> {
                        (binding.textInputDescription).run {
                            isErrorEnabled = true; error = "Please Enter Description";requestFocus()
                        }
                    }
                    else ->{
                        val newTask = Task()
                        newTask.id = taskList.get(position).id
                        newTask.title = binding.etTitle.text.toString().trim()
                        newTask.description = binding.etDescription.text.toString().trim()
                        DatabaseHelper.updateTaskData(newTask)
                        taskList = DatabaseHelper.getTaskAllData() as ArrayList<Task>
                        notifyDataSetChanged()
                        binding.constraintOne.visibility = View.VISIBLE
                        binding.constraintTwo.visibility = View.GONE
                    }
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(position)
    }

    override fun getItemCount() = taskList.size
}