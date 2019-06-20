package com.alexkn.syntact.presentation.flashcard

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexkn.syntact.app.TAG

import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.domain.repository.SolvableItemRepository
import io.reactivex.disposables.CompositeDisposable

import java.time.Instant

import javax.inject.Inject


class FlashcardViewModel @Inject
constructor(private val solvableItemRepository: SolvableItemRepository, private val bucketRepository: BucketRepository) : ViewModel() {

    internal var bucket: LiveData<Bucket>? = null
        private set

    internal var translations: LiveData<List<SolvableTranslationCto>>? = null
        private set

    private var disp: CompositeDisposable = CompositeDisposable()

    var solvableTranslations = Array<MutableLiveData<SolvableTranslationCto>>(2) { MutableLiveData() }

    private var bucketId: Long? = null

    fun init(bucketId: Long?) {

        this.bucketId = bucketId
        bucket = bucketRepository.getBucket(bucketId)
        translations = solvableItemRepository.getSolvableTranslations(bucketId)
    }

    fun fetchNext(one: Boolean) {
        disp.dispose()
        if (one) {
            AsyncTask.execute {
                val disposable = solvableItemRepository.getNextSolvableTranslations(bucketId, Instant.now(), 2).subscribe(
                        {
                            solvableTranslations[1].postValue(it[1])
                            solvableTranslations[0].postValue(it[0])
                        },
                        { Log.e(TAG, "fetchNext: Error fetching", it) },
                        { Log.i(TAG, "No Translation found") })

                disp.add(disposable)
            }
        } else {
            AsyncTask.execute {
                val disposable = solvableItemRepository.getNextSolvableTranslations(bucketId, Instant.now(), 2).subscribe(
                        {
                            solvableTranslations[0].postValue(it[1])
                            solvableTranslations[1].postValue(it[0])
                        },
                        { Log.e(TAG, "fetchNext: Error fetching", it) },
                        { Log.i(TAG, "No Translation found") })


                disp.addAll(disposable)
            }
        }
    }

    fun checkSolution(solution: String, one: Boolean): Boolean {
        if (one) {
            if (solvableTranslations[0].value?.solvableItem!!.text == solution) {
                AsyncTask.execute {
                    solvableItemRepository.solvePhrase(solvableTranslations[0].value!!)
                }
                return true

            }
        } else {
            if (solvableTranslations[1].value?.solvableItem!!.text == solution) {
                AsyncTask.execute {
                    solvableItemRepository.solvePhrase(solvableTranslations[1].value!!)
                }
                return true
            }
        }
        return false
    }
}
