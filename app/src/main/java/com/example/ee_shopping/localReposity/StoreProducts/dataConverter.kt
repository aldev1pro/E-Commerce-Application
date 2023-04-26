package com.example.ee_shopping.localReposity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.ee_shopping.userSerializeDataStore.ProductInformation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

interface Converter{
    fun convertBitmapToByteArray(bitmap: Bitmap):ByteArray
    fun convertByteArrayToBitmap(byteArray: ByteArray):Bitmap

}
class ConverterImpl:Converter{
    @TypeConverter
    override fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        return outputStream.toByteArray()
    }
    @TypeConverter
    override fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

}

