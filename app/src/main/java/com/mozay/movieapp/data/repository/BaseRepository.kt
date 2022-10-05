package com.mozay.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.mozay.movieapp.common.extensions.onException
import com.mozay.movieapp.common.extensions.onFailure
import com.mozay.movieapp.common.extensions.onSuccess
import com.mozay.movieapp.common.extensions.request
import com.mozay.movieapp.data.model.network.BaseListResponse
import com.mozay.movieapp.data.model.network.BasePageListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

abstract class BaseRepository {

    protected suspend fun <Type> loadCall(
        call: () -> Call<Type>,
        result: MutableLiveData<Type>,
        errorText: (String) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            call().request { response ->
                response.onSuccess { data?.let { result.postValue(it) } }
                response.onException { message?.let { errorText(it) } }
                response.onFailure { message?.let { errorText(it) } }
            }
            result.apply { postValue(null) }
        }

    protected suspend fun <Response : BaseListResponse<ListType>, ListType> loadListCall(
        call: () -> Call<Response>,
        result: MutableLiveData<List<ListType>>,
        errorText: (String) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            call().request { response ->
                response.onSuccess { data?.let { result.postValue((it).results) } }
                response.onException { message?.let { errorText(it) } }
                response.onFailure { message?.let { errorText(it) } }
            }
            result.apply { postValue(null) }
        }

    protected suspend fun <Response : BasePageListResponse<ListType>, ListType> loadPageListCall(
        call: () -> Call<Response>,
        result: MutableLiveData<List<ListType>>,
        errorText: (String) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            call().request { response ->
                response.onSuccess { data?.let { result.postValue((it).results) } }
                response.onException { message?.let { errorText(it) } }
                response.onFailure { message?.let { errorText(it) } }
            }
            result.apply { postValue(null) }
        }
}