package com.example.m.ismayilov.timetracker

import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class Users (var name:String , var email:String , var  password:String){

}