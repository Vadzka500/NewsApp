package com.example.newstestapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstestapp.data.api.repository.MainRepository
import com.example.newstestapp.models.NewsResponse
import com.example.newstestapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    var newsData = MutableLiveData<MyResponse<NewsResponse>>()
    fun getAllNotes() = viewModelScope.launch {
        repository.lastNews().collect{
            newsData.postValue(it)
        }
    }

}