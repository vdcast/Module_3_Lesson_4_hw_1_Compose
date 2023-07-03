package com.example.module_3_lesson_4_hw_1_composee

import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("fact")
    fun getRandomFact(): Call<ResponseMain>
}