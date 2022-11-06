package com.example.m.ismayilov.timetracker.onClick

import com.example.m.ismayilov.timetracker.UserProjectName

interface OnlineOnClickLIstener {
    fun onClickListenerId(id:Int , play:Boolean)
    fun onClickListenerAddProject(katagoryName: String)
    fun onClickSetHistory(phone: String )
    fun onClickSetDeleteUser(phone: String )
    fun onClickSetDeleteUserProject(phone: String  , projectName: String)
    fun onClickSetUserChangePojectName(phone: String , projectName:String )

}