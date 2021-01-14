package com.ibrajix.firebasenotificationandroid.network

import com.ibrajix.firebasenotificationandroid.helper.EndPoints
import com.ibrajix.firebasenotificationandroid.model.AuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

@JvmSuppressWildcards
interface ApiService {

    @FormUrlEncoded
    @POST(EndPoints.SAVE_TOKEN)
    suspend fun sendNotification (
        @Field("name") name: String,
        @Field("token") token: String)
            : Response<AuthResponse>

}