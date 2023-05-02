package com.example.storyapp.view.main

import StoryAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R

import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.utils.SesionManager
import com.example.storyapp.view.addstory.AddStoryActivity
import com.example.storyapp.view.login.LoginActivity

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var viewModel: MainViewModel

    private lateinit var sharedPref: SesionManager
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SesionManager(this)
        token = sharedPref.getToken

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.setHasFixedSize(true)
        adapter = StoryAdapter(this, arrayListOf())
        binding.rvStories.adapter = adapter

        viewModel.allStory.observe(this) {
            adapter.setData(it)
        }

        viewModel.errorMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getAllStory("Bearer $token")

        binding.btnAdd.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    AddStoryActivity::class.java
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_menu -> {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Apakah Anda yakin ingin keluar ?")
                    ?.setPositiveButton("Iya") { _, _ ->
                        sharedPref.clearData()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }
                    ?.setNegativeButton("Batal", null)
                val alert = alertDialog.create()
                alert.show()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}