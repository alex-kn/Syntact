package com.alexkn.syntact.domain.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.restservice.PhrasesRequest
import com.alexkn.syntact.restservice.SyntactService
import com.alexkn.syntact.restservice.TemplateRequest
import com.alexkn.syntact.restservice.TemplateType
import java.io.IOException
import java.util.*
import javax.inject.Inject

class CreateBucketWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    @Inject @JvmField
    var property: Property? = null

    @Inject @JvmField
    var syntactService: SyntactService? = null

    @Inject
    lateinit var bucketDao: BucketDao

    init {
        (context as ApplicationComponentProvider).applicationComponent.inject(this)
    }

    override fun doWork(): Result {

        val inputData = inputData

        val name = inputData.getString("name")!!
        val language = Locale(inputData.getString("language"))
        val words = inputData.getStringArray("words")!!

        val token = "Token " + property!!["api-auth-token"]

        val templateRequest = TemplateRequest(
                name = name,
                templateType = TemplateType.CUSTOM,
                language = language
        )
        try {
            val templateResponse = syntactService!!.postTemplate(token, templateRequest).execute()
            if (templateResponse.isSuccessful) {
                val template = templateResponse.body()!!

                Log.i(TAG, "New bucket created")

                val phraseResponse = syntactService!!.postPhrases(token, template.id.toLong(), Locale.getDefault().language,
                        words.map { PhrasesRequest(it, language) }.toList()).execute()

                if (phraseResponse.isSuccessful) {
                    Log.i(TAG, "New phrases created")
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error creating Bucket", e)
            return Result.failure()
        }

        return Result.success()
    }

}
