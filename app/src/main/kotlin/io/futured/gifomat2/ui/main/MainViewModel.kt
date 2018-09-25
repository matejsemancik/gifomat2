package io.futured.gifomat2.ui.main

import androidx.lifecycle.ViewModel
import io.futured.gifomat2.domain.gif.EncodeGifSingler
import io.futured.gifomat2.domain.slack.UploadSlackFileCompletabler
import timber.log.Timber
import java.io.File

class MainViewModel constructor(
        val uploadSlackFileCompletabler: UploadSlackFileCompletabler,
        val encodeGifSingler: EncodeGifSingler
) : ViewModel() {

    init {

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
                .init("gifomat-dev", file, "image/gif")
                .execute(
                        { Timber.d("Success") },
                        { Timber.e(it) }
                )
    }
}
