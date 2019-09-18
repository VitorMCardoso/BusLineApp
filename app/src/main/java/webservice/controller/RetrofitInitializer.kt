package webservice.controller

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import webservice.service.BusLineService

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://172.20.60.59:8080/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun busLineService() = retrofit.create(BusLineService::class.java)
}

