package com.jws.mobile.core.helpers

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val gson = Gson()
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun putInt(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putFloat(key: String?, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String?, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun getLong(key: String?, value: Long): Long {
        return sharedPreferences.getLong(key, value)
    }

    fun putLong(key: String?, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    fun putIntegerList(key: String?, list: List<Int?>?) {
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getIntegerList(key: String?): List<Int>? {
        val json = sharedPreferences.getString(key, null) ?: return null
        val type = object : TypeToken<List<Int?>?>() {
        }.type
        return gson.fromJson(json, type)
    }

    fun putStringList(key: String?, list: List<String?>?) {
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getStringList(key: String?): List<String>? {
        val json = sharedPreferences.getString(key, null) ?: return null
        val type = object : TypeToken<List<String?>?>() {
        }.type
        return gson.fromJson(json, type)
    }

    fun <T> getListModel(key: String?, clazz: Class<T>?): List<T>? {
        val json = sharedPreferences.getString(key, null) ?: return null

        val type = TypeToken.getParameterized(
            MutableList::class.java, clazz
        ).type
        return gson.fromJson(json, type)
    }

    fun <T> putListModel(key: String?, list: List<T>?) {
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun <T> putModel(key: String?, `object`: T) {
        val json = gson.toJson(`object`)
        editor.putString(key, json)
        editor.apply()
    }

    fun <T> getModel(key: String?, clazz: Class<T>?): T? {
        val json = sharedPreferences.getString(key, null) ?: return null

        return gson.fromJson(json, clazz)
    }

    fun getStringMap(key: String?): Map<String, String> {
        val json = sharedPreferences.getString(key, null)
            ?: return HashMap()

        val type = object : TypeToken<Map<String?, String?>?>() {
        }.type
        return gson.fromJson(json, type)
    }

    fun putStringMap(key: String?, map: Map<String?, String?>?) {
        val json = gson.toJson(map)
        editor.putString(key, json)
        editor.apply()
    }
}