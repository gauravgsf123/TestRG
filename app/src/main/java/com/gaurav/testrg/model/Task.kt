package com.gaurav.testrg.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(var id: Int, var title: String, var description: String):Parcelable{
    constructor():this(0,"","")
}
