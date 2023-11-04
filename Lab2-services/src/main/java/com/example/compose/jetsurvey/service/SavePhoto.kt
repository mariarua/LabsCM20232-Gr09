package com.example.compose.jetsurvey.service

import android.app.IntentService
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.compose.jetsurvey.survey.question.GeneradorMeme
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavePhoto : IntentService("SavePhoto") {
    override fun onHandleIntent(intent: Intent?) {
        val imageUrl = GeneradorMeme.uri
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(imageUrl)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val imageBytes = response.body?.bytes()
                if (imageBytes != null) {
                    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val imageFile = File(storageDir, "Meme-${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(
                        Date()
                    )}.jpg")
                    Log.d("UBICACIÓN", storageDir.toString())
                    Log.d("UBICACIÓN", imageFile.toString())
                    val outputStream = FileOutputStream(imageFile)
                    outputStream.write(imageBytes)
                    outputStream.close()


                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
