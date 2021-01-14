package com.ibrajix.firebasenotificationandroid.viewmodel

import androidx.lifecycle.ViewModel
import com.ibrajix.firebasenotificationandroid.network.ApiDataSource
import com.ibrajix.firebasenotificationandroid.network.BaseDataSource
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiDataSource: ApiDataSource): BaseDataSource() {

    suspend fun sendNotification(name: String, token: String) = safeApiCall { apiDataSource.sendNotification(name, token) }

}