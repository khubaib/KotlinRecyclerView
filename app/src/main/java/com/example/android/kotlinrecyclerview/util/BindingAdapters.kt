package com.example.android.kotlinrecyclerview.util

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Binding adapter used to hide the spinner once data is available.
 */
@BindingAdapter("isNetworkError", "sections")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, sections: Any?) {
    view.visibility =
        if (sections != null) {
            View.GONE
        }
         else {
            View.VISIBLE
        }

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}
