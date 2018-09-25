package io.futured.gifomat2.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraUtils
import io.futured.gifomat2.R
import io.futured.gifomat2.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
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
        binding.cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(jpeg: ByteArray?) {
//                Timber.d("Captured picture")

                val startMillis = System.currentTimeMillis()
                CameraUtils.decodeBitmap(jpeg) {
                    val endMillis = System.currentTimeMillis()
//                    Timber.d("Decoded, took ${endMillis - startMillis} ms")
                }

                viewModel.onImageCaptured()
            }
        })
    }

    override fun onCaptureClick() {
        binding.cameraView.captureSnapshot()
    }
}