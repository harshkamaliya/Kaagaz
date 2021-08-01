package com.example.camerax.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.camerax.CameraApplication
import com.example.camerax.CameraGetViewModelFactory
import com.example.camerax.CameraViewModelFactory
import com.example.camerax.databinding.ActivityHomePageBinding
import com.example.camerax.viewmodel.CameraGetViewModel
import com.example.camerax.viewmodel.CameraViewModel

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var cameraGetViewModel: CameraGetViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityHomePageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.fab.setOnClickListener {


            val intent = Intent(this@HomePage, MainActivity::class.java)
            startActivity(intent)

        }

     //   createDatabase()








    }
    private fun createDatabase() {

        val appClass = application as CameraApplication
        val userRepository = appClass.cameraGetRepository
        val userViewModelFactory = CameraGetViewModelFactory(userRepository)

        cameraGetViewModel=ViewModelProviders.of(this,userViewModelFactory).get(CameraGetViewModel::class.java)





    }
}