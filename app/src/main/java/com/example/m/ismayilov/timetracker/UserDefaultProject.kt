package com.example.m.ismayilov.timetracker

import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data  class UserDefaultProject (var projectName:String  = "" , var colorCode:String = ""){
}