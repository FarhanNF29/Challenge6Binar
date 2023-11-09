package com.example.challenge3binar


import android.content.Context
import androidx.core.content.edit


class SharedPreference(context: Context) {
    private val sharedPreferencesName = "SharedPreferenceBinarMart"
    private var preference = context.getSharedPreferences(sharedPreferencesName, 0)

    var isGrid: Boolean
        set(value) {
            preference.edit {
                putBoolean("IS_GRID", value)
            }
        }
        get() = preference.getBoolean("IS_GRID", true)

}