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
        sendSlackMessageCompletabler
                .init("gifomat", "Network module initialized.", true)
                .execute(
                        {

                        },
                        {
                            Timber.e(it)
                        }
                )
        Timber.d("Img captured")
    }
}
