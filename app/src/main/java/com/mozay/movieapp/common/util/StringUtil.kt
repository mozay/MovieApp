package com.mozay.movieapp.common

fun appendZeroBeforeNumber(num: Int): String {
    return if (num < 10) "0$num" else num.toString()
}