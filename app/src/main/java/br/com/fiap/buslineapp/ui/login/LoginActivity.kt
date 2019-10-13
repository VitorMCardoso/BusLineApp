package br.com.fiap.buslineapp.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.form.FormActivity
import br.com.fiap.buslineapp.ui.main.MainActivity
import br.com.fiap.buslineapp.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val newUserRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser?.reload()

        if (mAuth.currentUser != null) {
            goToHome()
        }

        btLogin.setOnClickListener {
            mAuth.signInWithEmailAndPassword(
                inputLoginEmail.text.toString(),
                inputLoginPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    goToHome()
                } else {
                    Toast.makeText(this@LoginActivity, it.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        btSignup.setOnClickListener {
            val nextScreen = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(nextScreen)
            finish()
        }

    }

    private fun goToHome() {
        var intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
            goToHome()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
