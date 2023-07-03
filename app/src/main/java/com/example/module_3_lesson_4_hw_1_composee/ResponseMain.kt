package com.example.module_3_lesson_4_hw_1_composee

import com.google.gson.annotations.SerializedName

data class ResponseMain(
    @SerializedName("fact") val fact : String,
    @SerializedName("length") val length : Int
)