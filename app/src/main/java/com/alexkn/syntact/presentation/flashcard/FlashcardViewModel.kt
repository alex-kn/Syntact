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

    private var translations: LiveData<List<SolvableTranslationCto>>? = null

    private var disp: CompositeDisposable = CompositeDisposable()

    var solvableTranslations = Array<MutableLiveData<SolvableTranslationCto?>>(2) { MutableLiveData() }

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
                            solvableTranslations[1].postValue(it.getOrNull(1))
                            solvableTranslations[0].postValue(it.getOrNull(0))
                        },
                        { Log.e(TAG, "Error", it) })

                disp.add(disposable)
            }
        } else {
            AsyncTask.execute {
                val disposable = solvableItemRepository.getNextSolvableTranslations(bucketId, Instant.now(), 2).subscribe(
                        {
                            solvableTranslations[0].postValue(it.getOrNull(1))
                            solvableTranslations[1].postValue(it.getOrNull(0))
                        },
                        { Log.e(TAG, "Error", it) })


                disp.addAll(disposable)
            }
        }
    }

    fun checkSolution(solution: String, one: Boolean): Boolean {
        if (one) {
            if (solvableTranslations[0].value?.solvableItem?.text.equals(solution, ignoreCase = true)) {
                AsyncTask.execute {
                    solvableItemRepository.solvePhrase(solvableTranslations[0].value!!)
                }
                return true

            }
        } else {
            if (solvableTranslations[1].value?.solvableItem?.text.equals(solution, ignoreCase = true)) {
                AsyncTask.execute {
                    solvableItemRepository.solvePhrase(solvableTranslations[1].value!!)
                }
                return true
            }
        }
        return false
    }
}
