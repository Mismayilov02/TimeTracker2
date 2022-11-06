package com.example.m.ismayilov.timetracker.onClick

import com.example.m.ismayilov.timetracker.room.Katagory

interface OnClickLIstener {
    fun onClickListenerId(katagory:Katagory)
    fun onClickListenerAction(katagoryName: String)
    fun onClickSetExpendValues(id: String , expend:Boolean)
    fun onClickSetDelete(katagoryName: String , projectName: String)
    fun onClickSetEditName(projectName: String , katagoryName: String)

}