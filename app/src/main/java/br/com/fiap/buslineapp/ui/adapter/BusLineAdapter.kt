package br.com.fiap.buslineapp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.DrawableWrapper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.form.FormActivity
import br.com.fiap.buslineapp.ui.main.MainActivity
import br.com.fiap.buslineapp.ui.model.BusLine
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.busline_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webservice.controller.RetrofitInitializer
import java.lang.System.out

class BusLineAdapter(private val context: Context, private val busLineList: MutableList<BusLine>) :
    Adapter<BusLineAdapter.BusLineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusLineViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.busline_item, parent, false)
        return BusLineViewHolder(view, context)
    }

    override fun getItemCount() = busLineList.size

    override fun onBindViewHolder(holder: BusLineViewHolder, position: Int) {
        val busline = busLineList[position]
        holder?.let {
            it.bindView(busline)
        }
    }

    class BusLineViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                println(it)
                val returnIntent = Intent(context, FormActivity::class.java)
                parseBus(returnIntent, it)
                startActivity(context, returnIntent, Bundle.EMPTY)
            }

            itemView.setOnLongClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete BusLIne")
                builder.setMessage("Are you want to delete BusLine")
                builder.setPositiveButton("YES") { dialog, which ->
                    // Do something when user press the positive button
                    Toast.makeText(context, "Ok, BusLine deleted.", Toast.LENGTH_SHORT).show()
                    val call =
                        RetrofitInitializer().busLineService()
                            .delete(itemView.textId.text.toString())
                    call.enqueue(object : Callback<BusLine> {

                        override fun onFailure(call: Call<BusLine>, t: Throwable) {
                            Log.e("onFailure error", t?.message)
                        }

                        override fun onResponse(call: Call<BusLine>, response: Response<BusLine>) {
                            val returnIntent = Intent(context, MainActivity::class.java)
                            startActivity(context, returnIntent, Bundle.EMPTY)
                        }
                    })

                }
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(context, "You are not agree.", Toast.LENGTH_SHORT).show()
                }

                builder.setNeutralButton("Cancel") { _, _ ->
                    Toast.makeText(context, "You cancelled the dialog.", Toast.LENGTH_SHORT).show()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                return@setOnLongClickListener true
            }
        }

        fun bindView(busLine: BusLine) {
            val id = itemView.textId
            val textNumber = itemView.textNumber
            val textListStreets = itemView.textListStreets

            id.text = busLine.id.toString()
            textNumber.text = busLine.busLine.toString()
            textListStreets.text = busLine.line.toString()
        }

        fun parseBus(intent: Intent, itemView: View) {
            intent.putExtra("isUpdate", true)
            intent.putExtra("busId", itemView.textId.text)
            intent.putExtra("busNumber", itemView.textNumber.text)
            intent.putExtra("busStreets", itemView.textListStreets.text)
        }
    }

}

