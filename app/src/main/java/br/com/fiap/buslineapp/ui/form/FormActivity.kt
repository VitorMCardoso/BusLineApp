package br.com.fiap.buslineapp.ui.form

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import br.com.fiap.buslineapp.R
import kotlinx.android.synthetic.main.activity_form.*


class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
    }

    fun onAddField() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.field, null)
        // Add the new row before the add field button.
        parent_linear_layout?.addView(rowView, parent_linear_layout.getChildCount() - 1)
    }

    fun onDeleteField() {
        if (parent_linear_layout.childCount >= 4) {
            parent_linear_layout?.removeViewAt(parent_linear_layout.childCount - 2)
        } else {
            Toast.makeText(this, "Não é possivel excluir mais linhas", Toast.LENGTH_SHORT).show()
        }

    }
}
