package com.example.netbond.models

data class User (
    var profile_image: String? = null,
    var name: String? = null,
    var username: String? = null,
    var n_followings: Int? = null,
    var n_points: Int? = null,
    var n_followers: Int? = null,
    var email: String? = null,
)