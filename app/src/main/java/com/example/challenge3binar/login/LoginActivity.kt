package com.example.challenge3binar.login


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge3binar.databinding.ActivityLoginBinding
import com.example.challenge3binar.koin.LoginViewModel
import com.example.challenge3binar.main.MainActivity
import com.example.challenge3binar.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Check if the user is already logged in
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            navigateToMainActivity()
            return
        }

        binding.tvNavToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLog.text.toString()
            val password = binding.etPasswordLog.text.toString()

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi dengan benar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Menggunakan ViewModel dengan Koin DI
            loginViewModel.login(email, password)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        // Observing ViewModel state
        loginViewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginViewModel.LoginState.Success -> {
                    // Save the login status in Shared Preferences
                    val editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    Toast.makeText(this, "Selamat datang ${state.email}", Toast.LENGTH_SHORT).show()
                    navigateToMainActivity()
                }
                is LoginViewModel.LoginState.Failure -> {
                    Toast.makeText(this, "Maaf, akun Anda belum terdaftar", Toast.LENGTH_SHORT).show()
                }
                // Handle other states if needed
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
