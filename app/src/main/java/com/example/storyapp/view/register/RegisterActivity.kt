package com.example.storyapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.view.login.LoginActivity



class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        supportActionBar!!.hide()

        binding.btnToLogin.setOnClickListener { toLogin() }

        binding.btnRegister.setOnClickListener { checkRegister() }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        registerViewModel.regisResut.observe(this){
            Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
            if (it == "User created") {
                val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
            } else{
                Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
            }
        }


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
            showLoading(true)
            registerViewModel.registerUser(name, email, password)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}