package com.example.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.utils.Constant.KEY_IS_LOGIN
import com.example.storyapp.utils.Constant.KEY_NAME
import com.example.storyapp.utils.Constant.KEY_TOKEN
import com.example.storyapp.utils.Constant.KEY_USER_ID
import com.example.storyapp.view.register.RegisterActivity
import com.example.storyapp.utils.SesionManager
import com.example.storyapp.view.main.MainActivity


class LoginActivity : AppCompatActivity() {


    private lateinit var sharedPref: SesionManager
    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        supportActionBar!!.hide()
        sharedPref = SesionManager(this)

        binding.btnToRegister.setOnClickListener { toRegister() }

        binding.btnLogin.setOnClickListener { checkLogin() }

        loginViewModel.message.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun toRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
    }

    private fun checkLogin() {
        val email = binding.eetEmail.text.toString()
        val password = binding.petPassword.text.toString()

        if (!binding.eetEmail.text.isNullOrEmpty() && !binding.petPassword.text.isNullOrEmpty() && password.length >= 8) {
            loginViewModel.loginUser(email, password)
            observeLoginResult()
        } else {
            Toast.makeText(this, "Password must be 8 character", Toast.LENGTH_SHORT).show()
            if (binding.eetEmail.text.isNullOrEmpty()){
                binding.eetEmail.error = "Email Tidak Boleh Kosong"
            }
            if (binding.petPassword.text.isNullOrEmpty()){
                binding.petPassword.error = "Password must be 8 character"
            }
        }
    }

    private fun observeLoginResult() {
        loginViewModel.loginResult.observe(this) { loginResponse ->
            if (loginResponse?.loginResult != null) {
                val responseBody = loginResponse.loginResult
                sharedPref.apply {
                    setBooleanPref(KEY_IS_LOGIN, true)
                    setStringPref(KEY_TOKEN, responseBody.token)
                    setStringPref(KEY_USER_ID, responseBody.userId)
                    setStringPref(KEY_NAME, responseBody.name)
                }
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)

                showToast()
            } else {
                showToast()

            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(){
        loginViewModel.message.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val isLogin = sharedPref.isLogin
        if (isLogin) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
        }
    }
}