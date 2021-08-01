package com.example.camerax.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.camerax.CameraApplication
import com.example.camerax.CameraGetViewModelFactory
import com.example.camerax.CameraViewModelFactory
import com.example.camerax.adapter.ParentImageAdapter
import com.example.camerax.databinding.ActivityHomePageBinding
import com.example.camerax.model.CameraEntity
import com.example.camerax.viewmodel.CameraGetViewModel
import com.example.camerax.viewmodel.CameraViewModel

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var cameraGetViewModel: CameraGetViewModel
    private var imageList = mutableListOf<CameraEntity>()
    private lateinit var postAdapter:ParentImageAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityHomePageBinding.inflate(layoutInflater)
        setRecyclerAdatapter()
        setContentView(binding.root)
        val appClass = application as CameraApplication
        val userRepository = appClass.cameraGetRepository
        val userViewModelFactory = CameraGetViewModelFactory(userRepository)

        cameraGetViewModel=ViewModelProviders.of(this,userViewModelFactory).get(CameraGetViewModel::class.java)


        binding.fab.setOnClickListener {


            val intent = Intent(this@HomePage, MainActivity::class.java)
            startActivity(intent)

        }

        cameraGetViewModel.getUserList().observe(this, Observer {

            imageList.clear()
            imageList.addAll(it)
            Log.d("TAG", "TotalImage: " + imageList.size)
            postAdapter.notifyDataSetChanged()

        })

    }

    private fun setRecyclerAdatapter() {
        postAdapter = ParentImageAdapter(imageList)
        binding.rvParent.adapter=postAdapter

    }
}