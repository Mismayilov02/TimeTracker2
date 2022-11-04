package com.example.m.ismayilov.timetracker.onClick

import com.example.m.ismayilov.timetracker.UserProjectName

interface OnClickLIstener {
    fun onClickListenerId(id:Int , play:Boolean)
    fun onClickListenerAction(katagoryName: String)
    fun onClickSetExpendValues(id: String , expend:Boolean)
    fun onClickSetDelete(katagoryName: String , projectName: String)
    fun onClickSetEditName(projectName: String , katagoryName: String)

}