package com.example.globalaccelex

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CreditCardTextWatcher(
        private var separator: String = " - ",
        private var divider: Int = 5
): TextWatcher {
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
        if (getCardNumber(p0.toString()).length >= 6) {
            CoroutineScope(IO).launch {
                Log.v("DEBUG", "Text Changed ${getCardNumber(p0.toString())}")
            }
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