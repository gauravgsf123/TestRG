package com.gaurav.testrg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.testrg.R
import com.gaurav.testrg.database.DatabaseHelper
import com.gaurav.testrg.databinding.UserListItemBinding
import com.gaurav.testrg.model.Users
import java.util.regex.Matcher
import java.util.regex.Pattern

class UserAdapter(var userList: ArrayList<Users>): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    inner class MyViewHolder(var view : View) : RecyclerView.ViewHolder(view){
        val binding: UserListItemBinding = UserListItemBinding.bind(view)
        fun bindItems(i: Int) {
            var user = userList[i]
            binding.title.text = user.nama
            binding.description.text = user.email
            binding.etName.setText(user.nama)
            binding.etEmail.setText(user.email)
            binding.delete.setOnClickListener {
                DatabaseHelper.deleteUserData(userList.get(i).id)
                userList = DatabaseHelper.getUserAllData() as ArrayList<Users>
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
            binding.update.setOnClickListener{
                when{
                    binding.etName.text.toString().isEmpty() -> {
                        (binding.textInputLayoutName).run {
                            isErrorEnabled = true; error = "Please Enter Name";requestFocus()
                        }
                    }
                    !isEmailValid(binding.etEmail.text.toString().trim()) -> {
                        (binding.textInputEmail).run {
                            isErrorEnabled = true; error = "Please Enter Valid Email";requestFocus()
                        }
                    }
                    else ->{
                        val newUser = Users()
                        newUser.id = user.id
                        newUser.nama = binding.etName.text.toString().trim()
                        newUser.email = binding.etEmail.text.toString().trim()
                        DatabaseHelper.updateUserData(newUser)
                        userList = DatabaseHelper.getUserAllData() as ArrayList<Users>
                        notifyDataSetChanged()
                        binding.constraintOne.visibility = View.VISIBLE
                        binding.constraintTwo.visibility = View.GONE
                    }
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(position)
    }

    override fun getItemCount() = userList.size

    open fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}