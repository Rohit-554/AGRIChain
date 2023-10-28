package io.jadu.agrichain.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object UtilityFunctions {
    fun convertLongDate(date: Long): String {
        val dateProcess = Date(date * 1000L) // Convert Unix timestamp to Date object
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Define date format
        return sdf.format(dateProcess)
    }

    fun getTodayDate(): Long {
        val currentTimeMillis =
            System.currentTimeMillis() // Current time in milliseconds since January 1, 1970, 00:00:00 UTC
        return currentTimeMillis / 1000L
    }


    fun stringToUnix(date: String): Long {
        val pattern = "MMMM d, yyyy"
        val timeZone = TimeZone.getTimeZone("UTC")

        val formatter = SimpleDateFormat(pattern, Locale.US)
        formatter.timeZone = timeZone

        val dateFormatter = formatter.parse(date)

        return dateFormatter?.time ?: 0
    }

    fun getImageBitmap(context: Context, imgUrl: String, callback: (Bitmap?) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(imgUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Optional: handle when the resource is cleared
                }
            })
    }


}