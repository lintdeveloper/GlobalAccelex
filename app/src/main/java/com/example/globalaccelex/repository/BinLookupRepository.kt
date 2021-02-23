package com.example.globalaccelex.repository

import BinLookUpResponse
import androidx.lifecycle.LiveData
import com.example.globalaccelex.models.Resource
import com.example.globalaccelex.services.BinLookupServiceBuilder
import kotlinx.coroutines.*


object BinLookupRepository {
    var job: CompletableJob? = null

    fun getBinLookUp(binLookUpNumber: String)
            : LiveData<Resource<BinLookUpResponse>> {
        job = Job()
        return object: LiveData<Resource<BinLookUpResponse>>(){
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        postValue(Resource.Loading(null))

                        try {
                            val lookUpResponse = BinLookupServiceBuilder.binLookupService.getBinLookUp(binLookUpNumber)

                            postValue(Resource.Success(lookUpResponse))
                            theJob.complete()
                        } catch (e: Exception) {
                            // A Network Issue, handle gracefully :)
                            postValue(Resource.Error(e))
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