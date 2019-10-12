package webservice.service

import br.com.fiap.buslineapp.ui.model.BusLine
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface BusLineService {

    @GET("busline")
    fun list(): Call<MutableList<BusLine>>

    @POST("busline")
    fun add(@Body busLine: BusLine): Call<BusLine>

    @PUT("busline")
    fun update(@Body busLine: BusLine): Call<BusLine>

    @DELETE("busline/{id}")
    fun delete(@Path("id") id: String): Call<BusLine>

}