package com.example.globalaccelex.`view-model`

import BinLookUpResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.globalaccelex.models.Resource
import com.example.globalaccelex.repository.BinLookupRepository

class MainViewModel: ViewModel() {

    private val _binLookUpNumber: MutableLiveData<String> = MutableLiveData()
    val lookUpResponse: LiveData<Resource<BinLookUpResponse>> = Transformations
        .switchMap(_binLookUpNumber){ lookUpNumber ->
            BinLookupRepository.getBinLookUp(lookUpNumber)
        }

    fun setBinLookUpNumber(binLookUpNumber: String) {
        val  update = binLookUpNumber
        if (_binLookUpNumber.value == update) {
            return
        }

        _binLookUpNumber.value = update
    }

    fun cancelJobs() {
        BinLookupRepository.cancelJobs()
    }
}