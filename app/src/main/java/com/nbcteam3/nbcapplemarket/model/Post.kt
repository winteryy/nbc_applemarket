package com.nbcteam3.nbcapplemarket.model

data class Post(
    val id: Int,
    val img: Int, //원래는 URL String
    val title: String,
    val content: String,
    val seller: String,
    val price: Int,
    val location: String,
    val favorite: Int,
    val chat: Int
)
