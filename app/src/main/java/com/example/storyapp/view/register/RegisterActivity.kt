package com.example.storyapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.apihelper.ApiConfig
import com.example.storyapp.data.RegisterResponse
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.main.MainActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {



    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.btnToLogin.setOnClickListener { toLogin() }

        binding.btnRegister.setOnClickListener { checkRegister() }
    }

    private fun toLogin() {
        val i = Intent(this, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
    }

    private fun checkRegister() {
        val name = binding.netName.text.toString()
        val email = binding.eetEmail.text.toString()
        val password = binding.petPassword.text.toString()

        if (name.isEmpty()) {
            binding.netName.requestFocus()
        } else if (email.isEmpty()) {
            binding.eetEmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.petPassword.requestFocus()
        } else {
            sendAPI(name, email, password)
        }
    }

    private fun sendAPI(name: String, email: String, password: String) {
        showLoading(true)
        val apiService = ApiConfig.getApiService().register(name, email, password)
        apiService.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val i = Intent(this@RegisterActivity, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(i)
                    Toast.makeText(
                        this@RegisterActivity,
                        response.body()?.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    val loginFailed = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        RegisterResponse::class.java
                    )
                    Toast.makeText(this@RegisterActivity, loginFailed.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: " + t.message)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}