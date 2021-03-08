package com.gaurav.testrg.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import cn.pedant.SweetAlert.SweetAlertDialog
import com.gaurav.testrg.R
import com.gaurav.testrg.custom.RegularTextInputEditText
import java.util.regex.Matcher
import java.util.regex.Pattern

abstract class BaseActivity:AppCompatActivity() {
    protected lateinit var sharedPreference: SharedPreference
    protected lateinit var toolbar: Toolbar
    protected lateinit var TAG:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreference(this)
        TAG = this.javaClass.simpleName
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

    }

    protected open fun showSuccessr(title: String, message: String){
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .show()
    }
    protected open fun showError(title: String, message: String){
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .show()
    }

    open fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
    fun RegularTextInputEditText?.isEmailValidOrNot(): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this?.text.toString())
        return matcher.matches()
    }

    fun TextView?.getText(): String = this?.text?.toString() ?: ""

    fun TextView?.isEmpty(): Boolean = this?.text?.isEmpty() ?: false

    protected open fun startNewActivity(activity: Activity) {
        startActivity(Intent(this, activity::class.java))
        //overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
    }
    /*protected open fun startNewActvity(activity:Activity) {
        startActivity(Intent(this, activity::class.java))
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
    }*/

    protected open fun startNewActvity(context: Context?, cls: Class<*>?) {
        startActivity(Intent(context, cls))
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //overridePendingTransition(R.anim.no_animation, R.anim.slide_down)
    }

}