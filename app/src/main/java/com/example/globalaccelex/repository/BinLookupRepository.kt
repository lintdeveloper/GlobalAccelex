package com.example.globalaccelex.repository

import BinLookUpResponse
import androidx.lifecycle.LiveData
import com.example.globalaccelex.services.BinLookupServiceBuilder
import kotlinx.coroutines.*


object BinLookupRepository {
    var job: CompletableJob? = null

    fun getBinLookUp(binLookUpNumber: String)
            : LiveData<BinLookUpResponse> {
        job = Job()
        return object: LiveData<BinLookUpResponse>(){
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        val lookUpResponse = BinLookupServiceBuilder.binLookupService.getBinLookUp(binLookUpNumber)
                        withContext(Dispatchers.Main){
                            value = lookUpResponse
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }
}