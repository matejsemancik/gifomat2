package io.futured.gifomat2.domain.gif

import android.graphics.Bitmap
import com.thefuntasty.taste.BaseSingler
import com.waynejo.androidndkgif.GifEncoder
import io.reactivex.Single
import org.jcodec.api.FrameGrab
import org.jcodec.common.AndroidUtil
import org.jcodec.common.io.NIOUtils
import java.io.File
import java.io.FileOutputStream

class EncodeGifSingler constructor(private val cacheDir: File) : BaseSingler<File>() {

    lateinit var videoFile: File

    fun init(videoFile: File) = apply {
        this.videoFile = videoFile
    }

    override fun build(): Single<File> {

        return Single
                .fromCallable {
                    val startSec = 0.0
                    val frameCount = 30

                    val grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile))
                    grab.seekToSecondPrecise(startSec)

                    val bitmaps = mutableListOf<Bitmap>()

                    for (i in 0 until frameCount) {
                        val picture = grab.nativeFrame
                        val bitmap = AndroidUtil.toBitmap(picture).apply {
                            val tmpfile = File(cacheDir, "frame-${i + System.currentTimeMillis()}.jpeg").apply { createNewFile() }
                            compress(Bitmap.CompressFormat.JPEG, 70, FileOutputStream(tmpfile))
                        }

                        bitmaps.add(bitmap)
                    }

                    return@fromCallable bitmaps
                }
                .map { bitmaps ->
                    val outFile = File(cacheDir, "gif-${System.currentTimeMillis()}.gif").apply { createNewFile() }
                    val gifEncoder = GifEncoder()
                    val width = bitmaps[0].width
                    val height = bitmaps[0].height
                    gifEncoder.init(width, height, outFile.path, GifEncoder.EncodingType.ENCODING_TYPE_SIMPLE_FAST)
                    bitmaps.forEach {
                        gifEncoder.encodeFrame(it, 50)
                    }

                    gifEncoder.close()
                    return@map outFile
                }


    }
}
