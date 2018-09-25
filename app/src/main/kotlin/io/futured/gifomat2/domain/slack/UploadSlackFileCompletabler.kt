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
    lateinit var mimeType: String

    fun init(channel: String, file: File, mime: String) = apply {
        this.channel = channel
        this.file = file
        this.mimeType = mime
    }

    override fun build(): Completable {
        val requestFile = RequestBody.create(MediaType.parse(mimeType), file)
        return slackService.uploadSlackFile(
                MultipartBody.Part.createFormData("file", file.name, requestFile),
                RequestBody.create(MultipartBody.FORM, channel)
        )
    }
}
