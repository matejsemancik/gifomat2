package io.futured.gifomat2.domain.slack

import com.thefuntasty.taste.BaseCompletabler
import io.futured.gifomat2.domain.SlackService
import io.reactivex.Completable

class SendSlackMessageCompletabler constructor(
        private val slackService: SlackService
) : BaseCompletabler() {

    lateinit var channel: String
    lateinit var text: String
    var asUser: Boolean = false

    fun init(channel: String, text: String, asUser: Boolean) = apply {
        this.channel = channel
        this.text = text
        this.asUser = asUser
    }

    override fun build(): Completable = slackService.postSlackMessage(channel, text, asUser)
}
