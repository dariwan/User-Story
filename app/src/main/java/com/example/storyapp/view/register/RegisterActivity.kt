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
            if (it == "User created") {
                val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                showToast()
            } else{
                showToast()
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

        if (!binding.netName.text.isNullOrEmpty() && !binding.eetEmail.text.isNullOrEmpty() && password.length >= 8){
            showLoading(true)
            registerViewModel.registerUser(name, email, password)
        } else{
            Toast.makeText(this, "Password must be 8 character", Toast.LENGTH_SHORT).show()
            if (binding.netName.text.isNullOrEmpty()){
                binding.netName.error = "Nama Tidak boleh Kosong"
            }
            if (binding.eetEmail.text.isNullOrEmpty()){
                binding.eetEmail.error = "Email Tidak Boleh Kosong"
            }
            if (binding.petPassword.text.isNullOrEmpty()){
                binding.petPassword.error = "Password must be 8 character"
            }
        }

//        if (name.isEmpty()) {
//            binding.netName.requestFocus()
//        } else if (email.isEmpty()) {
//            binding.eetEmail.requestFocus()
//        } else if (password.isEmpty()) {
//            binding.petPassword.requestFocus()
//        } else if(password.length < 8 ){
//            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
//        } else {
//            showLoading(true)
//            registerViewModel.registerUser(name, email, password)
//        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(){
        registerViewModel.regisResut.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}