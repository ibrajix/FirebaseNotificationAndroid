package com.ibrajix.firebasenotificationandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ibrajix.firebasenotificationandroid.R
import com.ibrajix.firebasenotificationandroid.databinding.ActivityMainBinding
import com.ibrajix.firebasenotificationandroid.helper.Resource
import com.ibrajix.firebasenotificationandroid.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //viewBinding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var buttonSend: Button? = null
    private var name: EditText? = null
    private var loadingProgress: ProgressBar? = null

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        _binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        initView()

    }

    private fun initView(){

        loadingProgress = binding.loading
        buttonSend = binding.btnSend
        name = binding.etName

        //listen to click event
        buttonSend!!.setOnClickListener {

            //hide button
            buttonSend!!.visibility = View.GONE

            //show progress bar
            loadingProgress!!.visibility = View.VISIBLE

            //register user
            doRegisterUser()
        }

    }

    private fun doRegisterUser(){

        //get user notification token provided by firebase
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("token_failed", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val notificationToken = task.result
            val nameString = etName.text.toString()

           //store the user name
            mainViewModel.doSendNotification(nameString, notificationToken!!)
            setupObserver()
        })

      }

    private fun setupObserver(){

        //observe data obtained
        mainViewModel.sendNotification.observe(this, Observer {

            when(it.status){

                Resource.Status.SUCCESS ->{

                    if(it.data?.status == "success"){

                        //stop progress bar
                        loadingProgress!!.visibility = View.GONE
                        buttonSend!!.visibility = View.VISIBLE

                        //show toast message
                        Toast.makeText(this, "Notification sent successfully", Toast.LENGTH_LONG).show()
                    }

                    else if(it.data?.status == "fail"){

                        //stop progress bar
                        loadingProgress!!.visibility = View.GONE
                        buttonSend!!.visibility = View.VISIBLE

                        //something went wrong, show error message
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    }


                }
                Resource.Status.ERROR -> {

                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    loading!!.visibility = View.GONE
                    buttonSend!!.visibility = View.VISIBLE

                }
                Resource.Status.LOADING -> {

                    loading!!.visibility = View.VISIBLE
                    buttonSend!!.visibility = View.GONE

                }
            }

        })

    }


}