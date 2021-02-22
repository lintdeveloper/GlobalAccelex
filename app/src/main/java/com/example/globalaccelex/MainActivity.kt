package com.example.globalaccelex

import BinLookUpResponse
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.globalaccelex.`view-model`.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        observeLookUpResponseData()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.lookUpResponse.observe(this, Observer {res ->
            println("DEBUG: $res")
        })
        viewModel.setBinLookUpNumber("45717360")
    }

    private fun observeLookUpResponseData() {
        viewModel.lookUpResponse.observe(this, renderLookupResponseData)
    }

    // --> Observers
    private val  renderLookupResponseData = Observer<BinLookUpResponse> {
        Log.v("DEBUG", "Data Updated $it")
        networkScheme.text = it.scheme.toString().toUpperCase()
        networkScheme.setTextColor(Color.BLACK)
        country.text = it.country.emoji + " "+ it.country.name
        country.setTextColor(Color.BLACK)
        bankName.text = it.bank.name
        bankName.setTextColor(Color.BLACK)
        bankPhoneNo.text = it.bank.phone
        bankPhoneNo.setTextColor(Color.BLUE)
        creditCardType.text = it.type.toUpperCase()
        creditCardType.setTextColor(Color.BLACK)

        if (it.prepaid) {
            isPrepaid.text = "Yes";
            isPrepaid.setTextColor(Color.BLACK)
        } else {
            isPrepaid.text = "No"
            isPrepaid.setTextColor(Color.RED)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }


}