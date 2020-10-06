package com.nigam.articles.model

import com.google.gson.annotations.SerializedName

data class Blog(
    @SerializedName("id")
    val id: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("comments")
    val comments: Long,
    @SerializedName("likes")
    val likes: Long,
    @SerializedName("user")
    val user: List<User>,
    @SerializedName("media")
    val media: List<Media>
)