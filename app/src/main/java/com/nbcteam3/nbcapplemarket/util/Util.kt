package com.nbcteam3.nbcapplemarket.util

import java.text.DecimalFormat

fun Int.toPriceText(): String {
    val dec = DecimalFormat("#,###")
    return "${dec.format(this)}원"
}