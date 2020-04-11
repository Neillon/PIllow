package com.example.intro.utils.extensions

import android.content.Context
import android.net.ConnectivityManager

val Context.isConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }
