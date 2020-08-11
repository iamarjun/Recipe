package com.arjun.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var connectionLiveData: ConnectionLiveData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectionLiveData = ConnectionLiveData(this)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        connectionLiveData.observe(this) {
            viewModel.setIsNetworkConnected(it)
        }
        viewModel.setIsNetworkConnected(isConnected)

    }

}