package br.com.fiap.buslineapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.about.AboutActivity
import br.com.fiap.buslineapp.ui.adapter.BusLineAdapter
import br.com.fiap.buslineapp.ui.form.FormActivity
import br.com.fiap.buslineapp.ui.model.BusLine
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webservice.controller.RetrofitInitializer


class MainActivity : AppCompatActivity() {

    lateinit var busLineAdapter: BusLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = RetrofitInitializer().busLineService().list()
        call.enqueue(object : Callback<MutableList<BusLine>?> {
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
        })

        fab.setOnClickListener {
            val nextScreen = Intent(this@MainActivity, FormActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(nextScreen)
            finish()
        }

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
            R.id.action_about -> {
                val nextScreen = Intent(this@MainActivity, AboutActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(nextScreen)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
