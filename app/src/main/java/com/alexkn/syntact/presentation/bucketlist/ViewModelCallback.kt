package com.alexkn.syntact.presentation.bucketlist

import com.alexkn.syntact.data.model.Bucket

internal interface ViewModelCallback {

    fun delete(bucket: Bucket)
}
