package io.futured.gifomat2.domain.image

import android.graphics.Bitmap
import com.otaliastudios.cameraview.CameraUtils
import com.thefuntasty.taste.BaseSingler
import io.reactivex.Single
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class DecodeImageSingler(
        private val cacheDir: File
) : BaseSingler<File>() {

    lateinit var bytes: ByteArray

    fun init(bytes: ByteArray) = apply {
        this.bytes = bytes
    }

    override fun build(): Single<File> = Single.fromCallable {
        val bitmap = CameraUtils.decodeBitmap(bytes, 720, 480)
        val outFile = File(cacheDir, "img-${System.currentTimeMillis()}.jpeg").apply { createNewFile() }

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos)
        val bitmapdata = bos.toByteArray()

        val fos = FileOutputStream(outFile)
        fos.write(bitmapdata)

        fos.flush()
        fos.close()

        bos.flush()
        bos.close()

        return@fromCallable outFile
    }

}
