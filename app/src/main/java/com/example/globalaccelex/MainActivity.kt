package com.example.globalaccelex

import BinLookUpResponse
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.globalaccelex.`view-model`.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel();
        observeLookUpResponseData()
        creditCardInput.addTextChangedListener(textWatcher)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.lookUpResponse.observe(this, Observer {res ->
            println("DEBUG: $res")
        })
    }

    private fun observeLookUpResponseData() {
        viewModel.lookUpResponse.observe(this, renderLookupResponseData)
    }

    // --> Observers
    private val  renderLookupResponseData = Observer<BinLookUpResponse> {
        Log.v("DEBUG", "Data Updated $it")
        it?.let {it ->
            networkScheme.text = it.scheme.toUpperCase()
            networkScheme.setTextColor(Color.BLACK)
            creditCardType.text = it.type.toUpperCase()
            creditCardType.setTextColor(Color.BLACK)
        }
        it.country?.let {it ->
            country.text = it.emoji + " "+ it.name
            country.setTextColor(Color.BLACK)
        }
        it.bank?.let {it ->
            bankName.text = it.name
            bankName.setTextColor(Color.BLACK)
            bankPhoneNo.text = it.phone
            bankPhoneNo.setTextColor(Color.BLUE)
        }

        if (it.prepaid) {
            isPrepaid.text = "Yes";
            isPrepaid.setTextColor(Color.BLACK)
        } else {
            isPrepaid.text = "No"
            isPrepaid.setTextColor(Color.RED)
        }
    }

    private val textWatcher = object : TextWatcher {
        private var separator: String = " - "
        private var divider: Int = 5

        override fun afterTextChanged(p0: Editable?) {
            if (p0 == null) {
                return
            }
            val oldString = p0.toString()
            val newString = getNewString(oldString)
            if (newString != oldString) {
                p0.replace(0, oldString.length, getNewString(oldString))
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val lookUpNumber = getCardNumber(p0.toString())
            if (lookUpNumber.length >= 6) {
                viewModel.setBinLookUpNumber(lookUpNumber)
            }
        }


        private fun getCardNumber(value: String): String {
            return value.replace(" - ", "");
        }

        private fun getNewString(value: String): String {
            var newString = value.replace(separator, "")

            var divider = this.divider
            while (newString.length >= divider) {
                newString = newString.substring(0, divider - 1) + this.separator + newString.substring(divider - 1)
                divider += this.divider + separator.length - 1
            }
            return newString
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }


}