package com.example.challenge3binar.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.challenge3binar.R
import com.example.challenge3binar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()

        val navController = findNavController(R.id.fragmentContainerView)

        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.feedFragment, R.id.officialStoreFragment, R.id.wishListFragment, R.id.transaksiFragment))

        //setupActionBarWithNavController(navController, appBarConfiguration)

        binding.botNav.setupWithNavController(navController)

        val bottomNavigationView = binding.botNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController1 = navHostFragment.navController

        navController1.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentHome -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentKeranjang -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentProfile2 -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }
    }

    private fun writeExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED

    private fun locationForegroundPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    private fun locationBackgroundPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    private fun requestPermission(){
        var requestPermissionUser = mutableListOf<String>()
        if(!writeExternalStoragePermission()){
            requestPermissionUser.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(!locationForegroundPermission()){
            requestPermissionUser.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if(!locationBackgroundPermission()){
            requestPermissionUser.add(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        if(requestPermissionUser.isNotEmpty()){
            ActivityCompat.requestPermissions(this, requestPermissionUser.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("PermissionUser", "${permissions[i]} berhasil dijalankan")
                }
            }
        }
    }
}