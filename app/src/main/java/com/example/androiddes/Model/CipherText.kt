package com.example.androiddes.Model

import java.io.Serializable

class CipherText(title: String, cipherText: String, createDate: String) : Serializable {
    val cipherText: String = cipherText
    val title: String = title
    val createDate: String = createDate
}