package com.nbcteam3.nbcapplemarket.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.nbcteam3.nbcapplemarket.R
import java.text.DecimalFormat

/**
 * Int형 정수를 가격 텍스트로
 *
 * @return String | 천 단위 콤마가 찍힌 'x원'
 */
fun Int.toPriceText(): String {
    val dec = DecimalFormat("#,###")
    return "${dec.format(this)}원"
}

/**
 * 다이얼로그 생성용 확장함수
 *
 * @param title     다이얼로그의 제목
 * @param msg       다이얼로그의 내용
 * @param callBack  다이얼로그의 positiveButton 클릭 시, 동작할 콜백 함수
 */
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