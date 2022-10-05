package com.mozay.movieapp.presentation.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bind_visibility_null_list")
fun View.bindViewVisibilityNullList(items: List<Any>?) {
    if (items == null) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}