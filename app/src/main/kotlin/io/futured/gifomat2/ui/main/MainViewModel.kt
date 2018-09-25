package io.futured.gifomat2.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.futured.gifomat2.domain.image.DecodeImageSingler
import io.futured.gifomat2.domain.slack.SendSlackMessageCompletabler
import io.futured.gifomat2.domain.slack.UploadSlackFileCompletabler
import timber.log.Timber
import java.io.File

class MainViewModel constructor(
        val sendSlackMessageCompletabler: SendSlackMessageCompletabler,
        val decodeImageSingler: DecodeImageSingler,
        val uploadSlackFileCompletabler: UploadSlackFileCompletabler
) : ViewModel() {

    val title: MutableLiveData<String> = MutableLiveData()

    init {
        title.value = "Hello zmrd!"
    }

    fun onImageCaptured(bytes: ByteArray) {
        decodeImageSingler.init(bytes).execute(
                { sendImage(it) },
                { Timber.e(it) }
        )
    }

    fun sendImage(file: File) {
        uploadSlackFileCompletabler
                .init("gifomat", file)
                .execute(
                        { Timber.d("Success") },
                        { Timber.e(it) }
                )
    }
}
