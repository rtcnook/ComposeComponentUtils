package com.example.widgetutilslib.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.Camera
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

/**
 * @author qlw
 **/
object FileUtils {

    private const val TAG = "CAMERA_666"

    fun decodeToBitMap(data: ByteArray?, camera: Camera): Bitmap? {
        return try {
            val parameters = camera.parameters
            val width = parameters.previewSize.width
            val height = parameters.previewSize.height
            //格式成YUV格式
            val yuvimage = YuvImage(data, ImageFormat.NV21, width, height, null)
            val baos = ByteArrayOutputStream()
            yuvimage.compressToJpeg(Rect(0, 0, width, height), 100, baos)
            val bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().size)
            val matrix = Matrix()
            matrix.setRotate(90f, bitmap.width / 2.0f, bitmap.height / 2.0f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveBitmap(pp: String?, bm: Bitmap): String {
        val file = File(pp, "/face_" + formatDate() + ".jpeg")
        try {
            val out = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return file.absolutePath
    }

    private fun formatDate(): String {
        val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun saveFile(pp: String?): String {
        val save = File(pp, "v-" + formatDate() + ".mp4")
        try {
            if (!save.exists()) {
                save.createNewFile()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return save.absolutePath
    }

    fun deleteFile(vararg fs: File?) {
        if (fs.isNotEmpty()) {
            for (f in fs) {
                if (f == null) continue
                val fd = File(f.absolutePath)
                LogUtils.d("delete file " + fd.absolutePath)
                if (fd.exists() && fd.isDirectory) {
                    fd.listFiles()?.forEach { fc ->
                        fc.delete()
                    }
                }
            }
        }
    }

    fun delete7File(fm: File?) {
        if (fm != null) {
            if (fm.exists() && fm.isDirectory) {
                fm.listFiles()?.forEach { fc ->
                    if (System.currentTimeMillis() - fc.lastModified() >= 7 * 24 * 60 * 60 * 1000L) {
                        fc.delete()
                    }
                }
            }
        }
    }

    fun useYuvImgSaveFile(pp: String?, imageProxy: ImageProxy, outputYOnly: Boolean): String {
        val wid = imageProxy.width
        val height = imageProxy.height
        Log.d(TAG, "宽高: $wid, $height")

        val yuvImage = toYuvImage(imageProxy)
        val file = File(pp, "/face_" + formatDate() + ".jpg")
        saveYuvToFile(file, wid, height, yuvImage)
        Log.d(TAG, "rustfisher.com 存储路径: $file")

        if (outputYOnly) { // 仅仅作为功能演示
            val yImg = toOnlyYuvImage(imageProxy)
            val yFile = File(Environment.getExternalStorageDirectory(), "y_" + System.currentTimeMillis() + ".png")
            saveYuvToFile(yFile, wid, height, yImg)
            Log.d(TAG, "rustfisher.com 存储路径: $yFile")
        }
        return file.absolutePath
    }

    fun toOnlyYuvImage(imageProxy: ImageProxy): YuvImage {
        if (imageProxy.format != ImageFormat.YUV_420_888) {
            throw IllegalArgumentException("Invalid image format")
        }
        val width = imageProxy.width
        val height = imageProxy.height
        val yBuffer = imageProxy.planes[0].buffer
        val numPixels = (width * height * 1.5f).toInt()
        val nv21 = ByteArray(numPixels)
        var index = 0
        val yRowStride = imageProxy.planes[0].rowStride
        val yPixelStride = imageProxy.planes[0].pixelStride
        for (y in 0 until height) {
            for (x in 0 until width) {
                nv21[index++] = yBuffer.get(y * yRowStride + x * yPixelStride)
            }
        }
        return YuvImage(nv21, ImageFormat.NV21, width, height, null)
    }

    fun toYuvImage(image: ImageProxy): YuvImage {
        if (image.format != ImageFormat.YUV_420_888) {
            throw IllegalArgumentException("Invalid image format")
        }
        val width = image.width
        val height = image.height

        // 拿到YUV数据
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val numPixels = (width * height * 1.5f).toInt()
        val nv21 = ByteArray(numPixels) // 转换后的数据
        var index = 0

        // 复制Y的数�?
        val yRowStride = image.planes[0].rowStride
        val yPixelStride = image.planes[0].pixelStride
        for (y in 0 until height) {
            for (x in 0 until width) {
                nv21[index++] = yBuffer.get(y * yRowStride + x * yPixelStride)
            }
        }

        // 复制U/V数据
        val uvRowStride = image.planes[1].rowStride
        val uvPixelStride = image.planes[1].pixelStride
        val uvWidth = width / 2
        val uvHeight = height / 2

        for (y in 0 until uvHeight) {
            for (x in 0 until uvWidth) {
                val bufferIndex = y * uvRowStride + x * uvPixelStride
                nv21[index++] = vBuffer.get(bufferIndex)
                nv21[index++] = uBuffer.get(bufferIndex)
            }
        }
        return YuvImage(nv21, ImageFormat.NV21, width, height, null)
    }

    fun saveYuvToFile(file: File, wid: Int, height: Int, yuvImage: YuvImage) {
        try {
            val c = file.createNewFile()
            Log.d(TAG, "$file created: $c")
            val fos = FileOutputStream(file)
            yuvImage.compressToJpeg(Rect(0, 0, wid, height), 100, fos)
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
