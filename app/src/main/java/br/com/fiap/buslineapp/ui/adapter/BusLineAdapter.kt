package br.com.fiap.buslineapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.model.BusLine
import kotlinx.android.synthetic.main.busline_item.view.*

class BusLineAdapter(private val context: Context, private val busLineList: MutableList<BusLine>) :
    Adapter<BusLineAdapter.BusLineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusLineViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.busline_item, parent, false)
        return BusLineViewHolder(view)
    }

    override fun getItemCount() = busLineList.size

    override fun onBindViewHolder(holder: BusLineViewHolder, position: Int) {
        val busline = busLineList[position]
        holder?.let {
            it.bindView(busline)
        }
    }

    class BusLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(busLine: BusLine) {
            val textNumber = itemView.textNumber
            val textListStreets = itemView.textListStreets

            textNumber.text = busLine.busLine.toString()
            textListStreets.text = busLine.line.toString()
        }
    }

}

