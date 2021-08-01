package com.example.camerax.ui

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.camerax.CameraApplication
import com.example.camerax.CameraViewModelFactory
import com.example.camerax.constrain.ConstantsData
import com.example.camerax.databinding.ActivityMainBinding
import com.example.camerax.model.CameraEntity
import com.example.camerax.viewmodel.CameraViewModel
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias lumaListener = (luma: Double) -> Unit


class MainActivity : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var albumName:String
   private var count:Int=0;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appClass = application as CameraApplication
        val userRepository = appClass.cameraRepository
        val userViewModelFactory = CameraViewModelFactory(userRepository)

        cameraViewModel=ViewModelProviders.of(this,userViewModelFactory).get(CameraViewModel::class.java)


        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, ConstantsData.REQUIRED_PERMISSIONS, ConstantsData.REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listener for take photo button
        binding.cameraCaptureButton.setOnClickListener { 
            takePhoto() 
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

      //database


//
//        val cameraEntity:CameraEntity(
//
//
//        )
//        cameraViewModel.addUser(cameraEntity)


    }
    

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val imageName=  SimpleDateFormat(FILENAME_FORMAT, Locale.US
        ).format(System.currentTimeMillis()) +".jpg"
        val photoFile = File(
            outputDirectory,imageName
          )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {

                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                  //  createDatabase()
                    val cameraEntity= CameraEntity(outputDirectory.toString(),imageName,savedUri.toString())

                    cameraViewModel.addUser(cameraEntity)


                    //showDialogBox()

                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }



    private fun allPermissionsGranted() = ConstantsData.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {

            File(it, "folders- ${System.currentTimeMillis()}").apply {
                mkdirs() }
//
//            for (i in 0 until mediaDir)
//            {
//                File filePath= new File(i);
//                File[] fileList = filePath.listFiles();
//                String name = fileList [0].toString();
//
//
//            }

        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir

    }



    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
       // private const val REQUEST_CODE_PERMISSIONS = 10
       // private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==ConstantsData.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(kotlinx.coroutines.Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer{
                          luma -> Log.d(TAG, "Average luminosity: $luma")
                    })
                }
            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,imageCapture,imageAnalyzer)

            } catch(exc: Exception) {

                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun createDatabase() {
        val appClass = application as CameraApplication
        val userRepository = appClass.cameraRepository
        val userViewModelFactory = CameraViewModelFactory(userRepository)

        cameraViewModel=ViewModelProviders.of(this,userViewModelFactory).get(CameraViewModel::class.java)


    }

    override fun onResume() {
        super.onResume()
//        binding.cameraCaptureButton.setOnClickListener {
//            takePhoto()
//        }

         getOutputDirectory()
        count++

      //  cameraExecutor = Executors.newSingleThreadExecutor()

    }




    private class LuminosityAnalyzer(private val listener: lumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

//    fun showDialogBox(){
//
//        val builder1 = AlertDialog.Builder(this)
//        builder1.setMessage("Write your message here.")
//        builder1.setTitle("Album Name")
//        val current_time=Calendar.getInstance().time
//        val input=EditText(this)
//        input.setHint("Enter Album Name")
//        input.inputType=InputType.TYPE_CLASS_TEXT
//        builder1.setView(input)
//        //set up the button
//
//        builder1.setPositiveButton(
//            "Yes",DialogInterface.OnClickListener{
//                dialog, which ->
//                 albumName = input.text.toString()
//                val cameraEntity=CameraEntity(current_time.toString(),albumName )
//
//            }
//
//
//        )
//
//
//
//        builder1.setCancelable(true)
//
//
//
//        builder1.setNegativeButton(
//            "No"
//        ) { dialog, id -> dialog.cancel() }
//
//        val alert11 = builder1.create()
//        alert11.show()
//
//    }










}


