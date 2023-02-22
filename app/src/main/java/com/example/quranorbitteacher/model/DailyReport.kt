package com.example.quranorbitteacher.model

data class DailyReport (
    var currentDate:String?=null,
    var dailCall:String?=null,
    var attendedCall:String?=null,
    var droppedCall:String?=null,
    var shiftedCall:String?=null,
    var uid:String?=null
        )