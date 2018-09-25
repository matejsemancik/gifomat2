package io.futured.gifomat2.di

import io.futured.gifomat2.R
import io.futured.gifomat2.domain.SlackService
import io.futured.gifomat2.domain.gif.EncodeGifSingler
import io.futured.gifomat2.domain.image.DecodeImageSingler
import io.futured.gifomat2.domain.slack.SendSlackMessageCompletabler
import io.futured.gifomat2.domain.slack.UploadSlackFileCompletabler
import io.futured.gifomat2.ui.main.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File

fun koinModules() = arrayListOf(appModule, interactorModule, networkModule)

val appModule = module {
    viewModel { MainViewModel(get(), get(), get(), get()) }
    single { androidContext().externalCacheDir } bind File::class
}

val interactorModule = module {
    factory { SendSlackMessageCompletabler(get()) }
    factory { DecodeImageSingler(get()) }
    factory { UploadSlackFileCompletabler(get()) }
    factory { EncodeGifSingler(get()) }
}

val networkModule = module {
    single {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        Retrofit.Builder()
                .baseUrl("https://slack.com/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder()
                        .addInterceptor(Interceptor { chain ->
                            var request = chain.request()
                            request = request
                                    .newBuilder()
                                    .addHeader("Authorization", androidContext().resources.getString(R.string.slack_auth_key))
                                    .build()
                            return@Interceptor chain.proceed(request)
                        })
                        .addInterceptor(logger)
                        .build())
                .build()
                .create(SlackService::class.java)
    } bind SlackService::class
}
