package io.futured.gifomat2.domain.slack

import com.thefuntasty.taste.BaseCompletabler
import io.futured.gifomat2.domain.SlackService
import io.reactivex.Completable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UploadSlackFileCompletabler constructor(
        private val slackService: SlackService
) : BaseCompletabler() {

    lateinit var channel: String
    lateinit var file: File

    fun init(channel: String, file: File) = apply {
        this.channel = channel
        this.file = file
    }

    override fun build(): Completable {
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        return slackService.uploadSlackFile(
                MultipartBody.Part.createFormData("file", file.name, requestFile),
                RequestBody.create(MultipartBody.FORM, channel)
        )
    }
}
