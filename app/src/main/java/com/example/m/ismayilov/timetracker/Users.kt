package com.example.m.ismayilov.timetracker

import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class Users(
    var name:String  ="", var phone:String ="", var password:String ="",
    var online:Boolean =false, var permission:Boolean = false,
    var history: UserProjectDate? = null , var project: Map<String, UserDeaultProjectName>? =null
                  ){

}