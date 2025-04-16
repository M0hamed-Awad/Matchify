package com.example.matchify.network

import com.example.matchify.models.api.FixturesApiResponse
import com.example.matchify.models.api.StandingsApiResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://v3.football.api-sports.io/"

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(FixturesApiResponse::class.java, CustomFixtureDeserializer())
        .registerTypeAdapter(StandingsApiResponse::class.java, CustomStandingDeserializer())
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}