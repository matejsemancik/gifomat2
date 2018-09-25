package io.futured.gifomat2.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.otaliastudios.cameraview.CameraListener
import io.futured.gifomat2.R
import io.futured.gifomat2.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File

class MainActivity : AppCompatActivity(), MainView {

    lateinit var binding: ActivityMainBinding
    val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.view = this
        binding.setLifecycleOwner(this)

        binding.cameraView.apply {
            setLifecycleOwner(this@MainActivity)
            addCameraListener(object : CameraListener() {
                override fun onVideoTaken(video: File?) {
                    video?.let { viewModel.onVideoCaptured(it) }
                }
            })

            Timber.d(cameraOptions.toString())
        }
    }

    override fun onGifCaptureClick() {
        val file = File(externalCacheDir, "vid-${System.currentTimeMillis()}").apply { createNewFile() }
        binding.cameraView.startCapturingVideo(file)
        viewModel.onVideoCaptureStart()
    }
}
