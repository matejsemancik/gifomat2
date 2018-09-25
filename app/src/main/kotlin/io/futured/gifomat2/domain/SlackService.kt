package io.futured.gifomat2.domain

import io.reactivex.Completable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SlackService {

    @FormUrlEncoded
    @POST("chat.postMessage")
    fun postSlackMessage(
            @Field("channel") channel: String,
            @Field("text") text: String,
            @Field("as_user") asUser: Boolean
    ): Completable

    @Multipart
    @POST("files.upload")
    fun uploadSlackFile(
            @Part file: MultipartBody.Part,
            @Part("channels") channels: RequestBody
    ): Completable
}
