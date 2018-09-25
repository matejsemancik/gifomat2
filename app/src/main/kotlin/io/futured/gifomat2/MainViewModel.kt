package io.futured.gifomat2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel constructor() : ViewModel() {

    val title: MutableLiveData<String> = MutableLiveData()

    init {
        title.value = "Hello zmrd!"
    }
}
