package com.example.challenge3binar.koin


import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// appModule.kt
val appModule = module {
    // Firebase Auth
    single { FirebaseAuth.getInstance() }

    // Shared Preferences
    single<SharedPreferences> { androidContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) }

    // ViewModel
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
}

