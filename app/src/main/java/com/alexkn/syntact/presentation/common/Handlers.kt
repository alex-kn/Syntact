package com.alexkn.syntact.presentation.common

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alexkn.syntact.app.TAG
import kotlinx.coroutines.CoroutineExceptionHandler

fun ViewModel.handler() = CoroutineExceptionHandler { _, exception ->
    Log.e(TAG, exception.message, exception)
}