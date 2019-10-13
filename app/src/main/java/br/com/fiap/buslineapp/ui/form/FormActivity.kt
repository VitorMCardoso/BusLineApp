package br.com.fiap.buslineapp.ui.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.main.MainActivity
import br.com.fiap.buslineapp.ui.model.BusLine
import kotlinx.android.synthetic.main.activity_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webservice.controller.RetrofitInitializer
import java.lang.StringBuilder
import java.lang.System.out
import kotlin.math.absoluteValue

class FormActivity : AppCompatActivity() {

    private var isUpdate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            isUpdate = bundle.getBoolean("isUpdate")

            if (isUpdate) {
                fillFields(bundle)
                btCreate.text = resources.getString(R.string.button_update_busline)
            }

        }

        btCreate.setOnClickListener {

            if (isUpdate) {
                val call = RetrofitInitializer().busLineService()
                    .update(fillObject(bundle?.getString("busId")!!, true))
                call.enqueue(object : Callback<BusLine> {

                    override fun onFailure(call: Call<BusLine>, t: Throwable) {
                        Log.e("onFailure error", t?.message)
                    }

                    override fun onResponse(call: Call<BusLine>, response: Response<BusLine>) {
                        val nextScreen = Intent(this@FormActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(nextScreen)
                        finish()
                    }
                })
            } else {
                val call = RetrofitInitializer().busLineService()
                    .add(fillObject("", false))
                call.enqueue(object : Callback<BusLine> {

                    override fun onFailure(call: Call<BusLine>, t: Throwable) {
                        Log.e("onFailure error", t?.message)
                    }

                    override fun onResponse(call: Call<BusLine>, response: Response<BusLine>) {
                        val nextScreen = Intent(this@FormActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(nextScreen)
                        finish()
                    }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        out.println(data?.getStringExtra("busStreets"))
    }

    fun fillFields(bundle: Bundle) {
        val listStreets = bundle.getString("busStreets")
        val busNumber = bundle.getString("busNumber")
        val arrayList: List<String> = listStreets!!.split(",").map {
            it.trim()
        }

        inputStreet.setText(arrayList.get(0).replace("[", ""))
        inputStreet2.setText(arrayList.get(1))
        inputStreet3.setText(arrayList.get(2))
        inputStreet4.setText(arrayList.get(3).replace("]", ""))
        inputNumberBus.setText(busNumber)


    }

    fun fillObject(busId: String, isUpdate: Boolean): BusLine {

        val id: String?

        if (isUpdate) {
            id = busId
        } else {
            id = null
        }

        val busLine = BusLine(
            id,
            inputNumberBus.text.toString().toBigDecimal(),
            mutableListOf(
                inputStreet.text.toString(),
                inputStreet2.text.toString(),
                inputStreet3.text.toString(),
                inputStreet4.text.toString()
            )
        )

        return busLine
    }

}


//////////////////////////
/*fun onAddField(v: View) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.field, null)
        // Add the new row before the add field button.

        jsonArray?.add(inputStreet.text.toString())

        if (parent_linear_layout.childCount >= 4 && count == 0) {
            jsonArray?.add(inputStreetCopy.text.toString())
            count = +1
        }*//*else if(parent_linear_layout.childCount >= 4 && count > 0){
            count++
            print(rowView)
        }*//*
        parent_linear_layout?.addView(rowView, parent_linear_layout.childCount - 1)
    }

    fun onDeleteField(v: View) {
        if (parent_linear_layout.childCount >= 4) {
            parent_linear_layout?.removeViewAt(parent_linear_layout.childCount - 2)
        } else {
            Toast.makeText(this, "Não é possivel excluir mais linhas", Toast.LENGTH_SHORT).show()
        }

    }*/
/////////////
