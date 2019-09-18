package webservice.service

import br.com.fiap.buslineapp.ui.model.BusLine
import retrofit2.Call
import retrofit2.http.GET

interface BusLineService {

    @GET("busline")
    fun list(): Call<MutableList<BusLine>>
}