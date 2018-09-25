package io.futured.gifomat2.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.otaliastudios.cameraview.Audio
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.SessionType
import com.otaliastudios.cameraview.VideoQuality
import io.futured.gifomat2.R
import io.futured.gifomat2.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class MainActivity : AppCompatActivity(), MainView {

    lateinit var binding: ActivityMainBinding
    val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.view = this

        binding.cameraView.setLifecycleOwner(this)
        binding.cameraView.sessionType = SessionType.VIDEO
        binding.cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(jpeg: ByteArray?) {
                jpeg?.let { viewModel.onImageCaptured(it) }
            }

            override fun onVideoTaken(video: File?) {
                video?.let { viewModel.onVideoCaptured(it) }
            }
        })
    }

    override fun onCaptureClick() {
        binding.cameraView.sessionType = SessionType.PICTURE
        binding.cameraView.captureSnapshot()
    }

    override fun onGifCaptureClick() {
        binding.cameraView.sessionType = SessionType.VIDEO
        binding.cameraView.audio = Audio.OFF
        binding.cameraView.videoQuality = VideoQuality.MAX_480P
        binding.cameraView.videoMaxDuration = 2000

        val file = File(externalCacheDir, "vid-${System.currentTimeMillis()}").apply { createNewFile() }
        binding.cameraView.startCapturingVideo(file)
    }
}
