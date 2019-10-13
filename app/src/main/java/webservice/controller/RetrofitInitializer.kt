package webservice.controller

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import webservice.service.BusLineService

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api-busline.herokuapp.com/")
        //.baseUrl("http://192.168.15.21:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun busLineService() = retrofit.create(BusLineService::class.java)
}

