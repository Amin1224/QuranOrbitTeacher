package com.example.quranorbitteacher.model

data class User(
    var userName: String? = null,
    var userEmail: String? = null,
    var password: String? = null,
    var notActive:Boolean?=null,
    var phoneNo:String?=null,
    var accountCreationDate:String?=null,
    var uid: String?=null,
    var studentUid:String?=null,
    var profileImage:String?=null
)