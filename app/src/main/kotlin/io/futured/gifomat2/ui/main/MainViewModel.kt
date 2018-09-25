package io.futured.gifomat2.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.futured.gifomat2.domain.gif.EncodeGifSingler
import io.futured.gifomat2.domain.slack.UploadSlackFileCompletabler
import timber.log.Timber
import java.io.File

class MainViewModel constructor(
        val uploadSlackFileCompletabler: UploadSlackFileCompletabler,
        val encodeGifSingler: EncodeGifSingler
) : ViewModel() {

    val captureButtonEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }
    val captureButtonText: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Make a GIF" }
    val isCaptureButtonRecording: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }

    fun onVideoCaptureStart() {
        captureButtonEnabled.value = false
        captureButtonText.value = "Recording..." // hackaton code lol, ain't nobody got time to write strings
        isCaptureButtonRecording.value = true
    }

    fun onVideoCaptured(videoFile: File) {
        captureButtonText.postValue("Processing GIF...")
        isCaptureButtonRecording.postValue(false)

        encodeGifSingler
                .init(videoFile)
                .execute(
                        {
                            captureButtonText.value = "Uploading..."
                            sendGif(it)
                        },
                        {
                            Timber.e(it)
                        }
                )
    }

    fun sendGif(file: File) {
        uploadSlackFileCompletabler
                .init("gifomat", file, "image/gif")
                .execute(
                        {
                            captureButtonText.value = "Make a GIF"
                            captureButtonEnabled.value = true
                        },
                        {
                            captureButtonText.value = "Make a GIF"
                            captureButtonEnabled.value = true
                            Timber.e(it)
                        }
                )
    }
}
