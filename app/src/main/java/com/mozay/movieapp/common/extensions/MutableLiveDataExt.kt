package com.mozay.movieapp.common.extensions

import androidx.lifecycle.MutableLiveData

fun <T, X : List<T>> MutableLiveData<MutableList<T>>.appendList(list: X) {
    val newList = this.value ?: mutableListOf()
    newList.addAll(list)
    this.value = newList
}
