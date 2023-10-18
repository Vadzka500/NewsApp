package com.example.newstestapp.data.api.repository

import android.util.Log
import com.example.newstestapp.data.api.NewsService
import com.example.newstestapp.models.NewsResponse
import com.example.newstestapp.utils.Constants
import com.example.newstestapp.utils.MyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val api : NewsService) {

    fun lastNews() : Flow<MyResponse<NewsResponse>> = flow {
        emit(MyResponse.loading())
        val response = api.getTopHeaders()
        if(response.isSuccessful)
            emit(MyResponse.success(response.body()))
        else
            emit(MyResponse.error("Try again later!"))



    }.catch {
        emit(MyResponse.error(it.message.toString()))
        Log.e("main", it.message.toString())
    }
}