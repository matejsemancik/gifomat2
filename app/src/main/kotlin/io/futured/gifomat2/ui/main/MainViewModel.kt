package io.futured.gifomat2.ui.main

import androidx.lifecycle.ViewModel
import io.futured.gifomat2.domain.gif.EncodeGifSingler
import io.futured.gifomat2.domain.image.DecodeImageSingler
import io.futured.gifomat2.domain.slack.SendSlackMessageCompletabler
import io.futured.gifomat2.domain.slack.UploadSlackFileCompletabler
import timber.log.Timber
import java.io.File

class MainViewModel constructor(
        val sendSlackMessageCompletabler: SendSlackMessageCompletabler,
        val decodeImageSingler: DecodeImageSingler,
        val uploadSlackFileCompletabler: UploadSlackFileCompletabler,
        val encodeGifSingler: EncodeGifSingler
) : ViewModel() {

    init {

    }

    fun onImageCaptured(bytes: ByteArray) {
        decodeImageSingler.init(bytes).execute(
                { sendImage(it) },
                { Timber.e(it) }
        )
    }

    fun sendImage(file: File) {
        uploadSlackFileCompletabler
                .init("gifomat", file, "image/jpeg")
                .execute(
                        { Timber.d("Success") },
                        { Timber.e(it) }
                )
    }

    fun onVideoCaptured(videoFile: File) {
        encodeGifSingler
                .init(videoFile)
                .execute(
                        { sendGif(it) },
                        { Timber.e(it) }
                )
    }

    fun sendGif(file: File) {
        uploadSlackFileCompletabler
                .init("gifomat", file, "image/gif")
                .execute(
                        { Timber.d("Success") },
                        { Timber.e(it) }
                )
    }
}
