package com.example.storyapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.adapter.StoryAdapter

import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.utils.SesionManager
import com.example.storyapp.view.addstory.AddStoryActivity
import com.example.storyapp.view.darkmode.DarkModeActivity
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.maps.MapsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels{
        MainViewModel.ViewModelFactory(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = StoryAdapter()

        binding.rvStories.adapter = adapter
        binding.rvStories.layoutManager = LinearLayoutManager(this)

        binding.rvStories.setHasFixedSize(true)


        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )

        viewModel.story.observe(this){
            adapter.submitData(lifecycle, it)
        }

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
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu -> {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Apakah Anda yakin ingin keluar ?")
                    ?.setPositiveButton("Iya") { _, _ ->
                        SesionManager(this).clearData()
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
            R.id.dark_mode_menu -> {
                val intent = Intent(this, DarkModeActivity::class.java)
                startActivity(intent)
            }

            R.id.maps_menu -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)

    }
}