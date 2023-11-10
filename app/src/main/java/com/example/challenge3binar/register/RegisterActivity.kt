package com.example.challenge3binar.register

// RegisterActivity.kt

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge3binar.databinding.ActivityRegisterBinding
import com.example.challenge3binar.koin.RegisterViewModel
import com.example.challenge3binar.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegist.setOnClickListener {
            val email = binding.etEmailReg.text.toString()
            val name = binding.etUsername.text.toString()
            val password = binding.etPasswordReg.text.toString()
            val confirmPass = binding.etConfirmpasswordReg.text.toString()


            //Validasi Nama
            if(name.isEmpty()) {
                binding.etUsername.error = "Username Harus Diisi"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }

            //Validasi email
            if (email.isEmpty()) {
                binding.etEmailReg.error = "Email Harus Diisi"
                binding.etEmailReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmailReg.error = "Email Tidak Valid"
                binding.etEmailReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()) {
                binding.etPasswordReg.error = "Password Harus Diisi"
                binding.etPasswordReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi panjang password
            if (password.length < 6) {
                binding.etPasswordReg.error = "Password Minimal 6 Karakter"
                binding.etPasswordReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi Confirm password
            if (confirmPass.isEmpty()) {
                binding.etConfirmpasswordReg.error = "Confirm Password Harus Diisi"
                binding.etConfirmpasswordReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi password sama dengan confirm password
            if (password != confirmPass) {
                Toast.makeText(this, "Password dan Confirm Password tidak sama", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            viewModel.register(email, password, name, this)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.registrationResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
