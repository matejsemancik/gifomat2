package io.futured.gifomat2.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.futured.gifomat2.domain.slack.SendSlackMessageCompletabler
import timber.log.Timber

class MainViewModel constructor(
        val sendSlackMessageCompletabler: SendSlackMessageCompletabler
) : ViewModel() {

    val title: MutableLiveData<String> = MutableLiveData()

    init {
        title.value = "Hello zmrd!"
    }

    fun onImageCaptured() {
        Timber.d("Img captured")
    }
}
