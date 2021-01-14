package com.ibrajix.firebasenotificationandroid.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrajix.firebasenotificationandroid.helper.Resource
import com.ibrajix.firebasenotificationandroid.helper.SingleLiveEvent
import com.ibrajix.firebasenotificationandroid.model.AuthResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(private val mainRepo: MainRepo) : ViewModel() {


    private val _sendNotification = SingleLiveEvent<Resource<AuthResponse>>()

    val sendNotification : LiveData<Resource<AuthResponse>> get() =  _sendNotification


    fun doSendNotification(name: String, token: String) =
        viewModelScope.launch {
            try {
                _sendNotification.value =  mainRepo.sendNotification(name, token)
            }
            catch (exception: Exception){

            }
        }


}