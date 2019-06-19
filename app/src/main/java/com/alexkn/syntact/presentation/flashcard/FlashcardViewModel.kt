package com.alexkn.syntact.presentation.flashcard

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.alexkn.syntact.domain.model.Bucket
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets
import com.alexkn.syntact.domain.usecase.play.ManageSolvableItems
import io.reactivex.disposables.CompositeDisposable

import java.time.Instant

import javax.inject.Inject

private const val TAG = "FlashcardViewModel"

class FlashcardViewModel @Inject
constructor(private val manageSolvableItems: ManageSolvableItems, private val manageBuckets: ManageBuckets) : ViewModel() {


    private val startTime: Instant

    internal var bucket: LiveData<Bucket>? = null
        private set

    internal var translations: LiveData<List<SolvableTranslationCto>>? = null
        private set

    var disp: CompositeDisposable = CompositeDisposable()

    var solvableTranslations = Array<MutableLiveData<SolvableTranslationCto>>(5) { MutableLiveData() }

    private var bucketId: Long? = null

    init {
        this.startTime = Instant.now()
    }

    fun init(bucketId: Long?) {

        this.bucketId = bucketId
        bucket = manageBuckets.getBucket(bucketId)
        translations = manageSolvableItems.getSolvableTranslations(bucketId)
    }

    fun triggerPhrasesFetch() {
        AsyncTask.execute { manageSolvableItems.fetchSolvableItems(bucketId!!, startTime) }
    }

    fun fetchNext(one: Boolean) {
        disp.dispose()
        if (one) {
            AsyncTask.execute {
                val disposable = manageSolvableItems.getNextSolvableTranslations(bucketId, startTime, 2).subscribe(
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
                val disposable = manageSolvableItems.getNextSolvableTranslations(bucketId, startTime, 2).subscribe(
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
                    manageSolvableItems.solvePhrase(solvableTranslations[0].value)
                }
                return true

            }
        } else {
            if (solvableTranslations[1].value?.solvableItem!!.text == solution) {
                AsyncTask.execute {
                    manageSolvableItems.solvePhrase(solvableTranslations[1].value)
                }
                return true
            }
        }
        return false
    }
}
