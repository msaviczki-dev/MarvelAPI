package com.msaviczki.marvel_api.helper.extension

import java.security.MessageDigest

fun String.toMd5(): String {
    val MD5 = "MD5"
    try {
        val digest = MessageDigest
            .getInstance(MD5)
        digest.update(toByteArray())
        val messageDigest = digest.digest()

        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    return ""
}