package webservice.service

import br.com.fiap.buslineapp.ui.model.BusLine
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BusLineService {

    @GET("api/busline")
    fun list(): Call<MutableList<BusLine>>

    @POST("api/busline")
    fun add(@Body busLine: BusLine): Call<BusLine>

    @PUT("api/busline")
    fun update(@Body busLine: BusLine): Call<BusLine>

    @DELETE("api/busline/{id}")
    fun delete(@Path("id") id: String): Call<BusLine>

    @GET("actuator/health")
    fun health(): Call<JsonElement>

}