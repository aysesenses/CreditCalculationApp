package com.aysesenses.creditcalculationapp.data.firebase

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfig {

    private const val TAG = "RemoteConfigUtils"

    private const val BANKS = "bank"
    private const val DEPOSITS = "deposit"

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init() {
        remoteConfig = getFirebaseRemoteConfig()
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        val updated = task.result
                        Log.d(TAG, "Config params: $updated")
                    }
                    else -> Log.d(TAG, "Fetch failed")
                }
            }
        return remoteConfig
    }
    fun getBank(): String = remoteConfig.getString(BANKS)
    fun getDeposit(): String = remoteConfig.getString(DEPOSITS)
}
