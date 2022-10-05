package com.mozay.movieapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mozay.movieapp.data.model.Event

open class BaseViewModel : ViewModel() {
    protected val mSnackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = mSnackBarText
}