package br.com.fiap.buslineapp.ui.model

data class BusLine(
    val id: String? = null,
    val busLine: Number,
    val line: MutableList<String>
)