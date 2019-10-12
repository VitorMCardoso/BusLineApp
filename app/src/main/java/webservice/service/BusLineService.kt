package webservice.service

import br.com.fiap.buslineapp.ui.model.BusLine
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface BusLineService {

    @GET("busline")
    fun list(): Call<MutableList<BusLine>>

    @POST("busline")
    fun add(@Body busLine: BusLine): Call<BusLine>

}