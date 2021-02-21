package com.example.globalaccelex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.globalaccelex.`view-model`.MainViewModel

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.lookUpResponse.observe(this, Observer {res ->
            println("DEBUG: $res")
        })

        viewModel.setBinLookUpNumber("45717360")

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }


}