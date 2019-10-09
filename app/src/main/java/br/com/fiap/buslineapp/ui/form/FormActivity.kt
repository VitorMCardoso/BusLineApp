package br.com.fiap.buslineapp.ui.form

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.main.MainActivity
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.field.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webservice.controller.RetrofitInitializer
import com.google.gson.JsonObject

class FormActivity : AppCompatActivity() {

    //private val myBusStreetsList: MutableList<String>? = ArrayList()
    private var count: Number = 0
    private val jsonArray: JsonArray? = JsonArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        btCreate.setOnClickListener {

            val jsonBusObject = JsonObject()

            jsonBusObject.addProperty("busLine", inputNumberBus.text.toString().toBigDecimal())
            jsonBusObject.add("line", jsonArray)

            val call = RetrofitInitializer().busLineService().add(jsonBusObject)
            call.enqueue(object : Callback<String> {

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("onFailure error", t?.message)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val nextScreen = Intent(this@FormActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(nextScreen)
                    finish()
                }
            }

            )
        }
    }

    fun onAddField(v: View) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.field, null)
        // Add the new row before the add field button.

        jsonArray?.add(inputStreet.text.toString())

        if (parent_linear_layout.childCount >= 4 && count == 0) {
            jsonArray?.add(inputStreetCopy.text.toString())
            count = +1
        }/*else if(parent_linear_layout.childCount >= 4 && count > 0){
            count++
            print(rowView)
        }*/
        parent_linear_layout?.addView(rowView, parent_linear_layout.childCount - 1)
    }

    fun onDeleteField(v: View) {
        if (parent_linear_layout.childCount >= 4) {
            parent_linear_layout?.removeViewAt(parent_linear_layout.childCount - 2)
        } else {
            Toast.makeText(this, "Não é possivel excluir mais linhas", Toast.LENGTH_SHORT).show()
        }

    }
}


/*call.enqueue(object : Callback<MutableList<BusLine>?> {
                override fun onResponse(
                    call: Call<MutableList<BusLine>?>?,
                    response: Response<MutableList<BusLine>?>?
                ) {
                    response?.body()?.let {
                        val buss: MutableList<BusLine> = it
                        configureList(buss)
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<BusLine>?>?,
                    t: Throwable?
                ) {
                    Log.e("onFailure error", t?.message)
                }
            })*/


/*for (i in 0 until parent_linear_layout.getChildCount())
    if (parent_linear_layout.getChildAt(i) is TextInputEditText)
        //myEditTextList.add(parent_linear_layout.getChildAt(i) as TextInputEditText)*/