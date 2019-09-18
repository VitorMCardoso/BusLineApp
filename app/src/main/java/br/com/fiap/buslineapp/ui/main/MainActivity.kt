package br.com.fiap.buslineapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.adapter.BusLineAdapter
import br.com.fiap.buslineapp.ui.model.BusLine
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webservice.controller.RetrofitInitializer


class MainActivity : AppCompatActivity() {

    lateinit var busLineAdapter: BusLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val call = RetrofitInitializer().busLineService().list()
        call.enqueue(object : Callback<MutableList<BusLine>?> {
            override fun onResponse(
                call: Call<MutableList<BusLine>?>?,
                response: Response<MutableList<BusLine>?>?
            ) {
                response?.body()?.let {
                    //val buslines: MutableList<BusLine> = it
                    print("AQUI>>>>$it")
                    val buss: MutableList<BusLine> = it
                    configureList(buss)
                    //busLineList.addAll(it)
                }
            }

            override fun onFailure(
                call: Call<MutableList<BusLine>?>?,
                t: Throwable?
            ) {
                Log.e("onFailure error", t?.message)
            }
        })
        print("AQIIII" + call)

    }

    private fun configureList(busLineList: MutableList<BusLine>) {
        busLineAdapter = BusLineAdapter(this, busLineList)
        recyclerViewBusLine.adapter = busLineAdapter
        recyclerViewBusLine.layoutManager = LinearLayoutManager(this)
        recyclerViewBusLine.smoothScrollToPosition(busLineList.size)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
