package com.nbcteam3.nbcapplemarket.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val img: Int, //원래는 URL String
    val title: String,
    val content: String,
    val seller: String,
    val price: Int,
    val location: String,
    val favoriteNum: Int,
    val chatNum: Int,
    val isFavorite: Boolean //좋아요 기능 용도
): Parcelable