package com.example.challenge3binar.koin

import org.koin.core.component.KoinComponent

// File: RegisterViewModel.kt

// RegisterViewModel.kt

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class RegisterViewModel : ViewModel(), KoinComponent {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val registrationResult: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun register(email: String, password: String, name: String, context: Context) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Simpan data registrasi ke SharedPreferences
                    val sharedPreferences =
                        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("username", name)
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.apply()

                    registrationResult.value = true
                } else {
                    errorMessage.value = task.exception?.message ?: "Registrasi gagal"
                }
            }
    }
}

