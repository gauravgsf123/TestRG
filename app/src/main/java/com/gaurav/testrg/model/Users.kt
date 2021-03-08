package com.gaurav.testrg.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(var id: Int, var nama: String, var email: String, var password: String):Parcelable{
    constructor() :this(0,"","","")

}
