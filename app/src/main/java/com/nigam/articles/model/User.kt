package com.nigam.articles.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("blogId")
    val blogId: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("about")
    val about: String
)