package com.example.m.ismayilov.timetracker.onClick

interface OnClickLIstener {
    fun onClickListenerId(id:Int , play:Boolean)
    fun onClickListenerAction(katagoryName: String)
    fun onClickSetExpendValues(id: String , expend:Boolean)
}