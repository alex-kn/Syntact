package com.alexkn.syntact.domain.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.restservice.SolvableItemService
import com.alexkn.syntact.restservice.SyntactService

import javax.inject.Inject

class FetchPhrasesWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val solvableItemDao: SolvableItemDao

    private val clueDao: ClueDao

    @Inject
    @JvmField
    var solvableItemService: SolvableItemService? = null

    @Inject
    @JvmField
    var syntactService: SyntactService? = null

    @Inject
    @JvmField
    var property: Property? = null

    @Inject
    lateinit var appDatabase: AppDatabase

    init {
        (context as ApplicationComponentProvider).applicationComponent.inject(this)

        solvableItemDao = appDatabase.solvableItemDao()
        clueDao = appDatabase.clueDao()
    }

    override fun doWork(): Result {

        //TODO do only if items on phone <= itemcount in bucket
        //TODO regularly update bucket phrase count
        //        Data inputData = getInputData();
        //        long bucketId = inputData.getLong("bucketId", -1L);
        //        long timestamp = inputData.getLong("timestamp", Instant.now().toEpochMilli());
        //        Instant now = Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.MINUTES);
        //        if (bucketId == -1L) {
        //            return Result.failure();
        //        }
        //
        //        List<SolvableTranslationCto> solvableTranslationCtos = solvableItemService.fetchNewTranslations(bucketId, now, 10);
        //        solvableTranslationCtos.forEach(cto -> {
        //            solvableItemDao.insert(cto.getSolvableItem());
        //            clueDao.insert(cto.getClue());
        //        });

        return Result.success()
    }

    companion object {

        private val TAG = FetchPhrasesWorker::class.java.simpleName
    }
}
