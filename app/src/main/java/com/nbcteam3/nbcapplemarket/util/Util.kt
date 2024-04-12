package com.nbcteam3.nbcapplemarket.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.nbcteam3.nbcapplemarket.R
import java.text.DecimalFormat

fun Int.toPriceText(): String {
    val dec = DecimalFormat("#,###")
    return "${dec.format(this)}원"
}

fun Activity.makeDialog(title: String, msg: String, callBack: () -> Unit) {
    val builder = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(msg)
        .setIcon(R.drawable.outline_chat_20)
        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(getString(R.string.confirm)) { _, _ ->
            callBack.invoke()
        }
    builder.create().show()
}