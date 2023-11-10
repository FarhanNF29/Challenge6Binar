package com.example.challenge3binar.koin


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LoginViewModel : ViewModel(), KoinComponent {

    private val auth: FirebaseAuth by inject()

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.value = LoginState.Success(email)
                } else {
                    _loginState.value = LoginState.Failure
                }
            }
    }

    sealed class LoginState {
        data class Success(val email: String) : LoginState()
        object Failure : LoginState()
        // Add other states if needed
    }
}
