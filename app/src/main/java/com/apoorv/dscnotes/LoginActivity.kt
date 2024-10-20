package com.apoorv.dscnotes


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.identity.android.legacy.Utility
import com.google.firebase.auth.FirebaseAuth
import com.apoorv.dscnotes.Utility.showToast

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var createAccountBtnTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginBtn = findViewById(R.id.login_btn)
        progressBar = findViewById(R.id.progress_bar)
        createAccountBtnTextView = findViewById(R.id.create_account_text_view_btn)

        loginBtn.setOnClickListener { loginUser() }
        createAccountBtnTextView.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (!validateData(email, password)) {
            return
        }

        loginAccountInFirebase(email, password)
    }

    private fun loginAccountInFirebase(email: String, password: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        changeInProgress(true)
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                changeInProgress(false)
                if (task.isSuccessful) {
                    if (firebaseAuth.currentUser?.isEmailVerified == true) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        showToast(this, "Email not verified, Please verify your email.")
                    }
                } else {
                    showToast(this, task.exception?.localizedMessage ?: "Login failed")
                }
            }
    }

    private fun changeInProgress(inProgress: Boolean) {
        progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
        loginBtn.visibility = if (inProgress) View.GONE else View.VISIBLE
    }

    private fun validateData(email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Email is invalid"
            return false
        }
        if (password.length < 6) {
            passwordEditText.error = "Password length is invalid"
            return false
        }
        return true
    }
}