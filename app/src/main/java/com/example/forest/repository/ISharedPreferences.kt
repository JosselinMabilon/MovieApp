package com.example.forest.repository

interface ISharedPreferences {

    fun putBooleanValue(key: String, value: Boolean)

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean
}