package br.com.fiap.buslineapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.model.BusLine
import kotlinx.android.synthetic.main.busline_item.view.*

class BusLineAdapter(private val context: Context, private val busLineList: MutableList<BusLine>) :
    RecyclerView.Adapter<BusLineAdapter.BusLineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusLineViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.busline_item, parent, false)
        return BusLineViewHolder(view)
    }

    override fun getItemCount() = busLineList.size

    override fun onBindViewHolder(holder: BusLineViewHolder, position: Int) {
        holder.bindView(busLineList[position])
    }

    class BusLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNumber = itemView.textNumber
        val textListStreets = itemView.textListStreets

        fun bindView(busLine: BusLine) {
            textNumber.text = busLine.busLine.toString()
            textListStreets.text = busLine.line.toString()
        }
    }

}

