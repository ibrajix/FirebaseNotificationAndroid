package com.ibrajix.firebasenotificationandroid.firebase

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ibrajix.firebasenotificationandroid.helper.Utility.sendNotification

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    //this is called when a message is received
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        //check messages
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload, you can get the payload here and add as an intent to your activity
        remoteMessage.data.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            //get the data
        }

        // Check if message contains a notification payload, send notification
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!)
        }

    }

    override fun onNewToken(token: String) {

        Log.d("rfst", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)

    }

    private fun sendRegistrationToServer(token: String?) {

        //you can send the updated value of the token to your server here

    }

    private fun sendNotification(messageBody: String){
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }


}