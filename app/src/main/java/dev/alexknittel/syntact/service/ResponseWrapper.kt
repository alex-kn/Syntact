package dev.alexknittel.syntact.service

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.HttpException

suspend fun <T : Any> handleRequest(requestFunc: suspend () -> T): T {
    return try {
        requestFunc.invoke()
    } catch (he: HttpException) {
        Log.e(TAG, "Service Error: ", he)
        throw he
    }
}