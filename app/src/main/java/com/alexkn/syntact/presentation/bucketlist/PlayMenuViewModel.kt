package com.alexkn.syntact.presentation.bucketlist

import android.os.AsyncTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.domain.repository.BucketRepository

import javax.inject.Inject

class PlayMenuViewModel @Inject
constructor(private val bucketRepository: BucketRepository) : ViewModel() {

    internal val buckets: LiveData<List<BucketDetail>> = bucketRepository.bucketDetails

    fun deleteLanguage(bucket: Bucket) {

        AsyncTask.execute { bucketRepository.removeLanguage(bucket) }
    }
}
