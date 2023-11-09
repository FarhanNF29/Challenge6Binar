package com.example.challenge3binar.viewModel.fragmentProfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.challenge3binar.databinding.FragmentProfileBinding
import com.example.challenge3binar.login.LoginActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout and initialize the binding
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data dari Shared Preferences
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")

        // Menampilkan data pada tampilan
        binding.etUserName.setText(username)
        binding.etEmail.setText(email)
        binding.etPassword.setText(password)

        // Menonaktifkan input pada EditText
        binding.etUserName.isFocusable = false
        binding.etUserName.isFocusableInTouchMode = false

        binding.etEmail.isFocusable = false
        binding.etEmail.isFocusableInTouchMode = false

        binding.etPassword.isFocusable = false
        binding.etPassword.isFocusableInTouchMode = false

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        // Hapus status login dari Shared Preferences
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        // Redirect user to the LoginActivity
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish() // Optional: Close the ProfileFragment

        // Menampilkan pesan logout
        showToast("Anda berhasil LogOut dari akun")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}