package com.example.manifesto.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
        get() = _name

    val _nameError: MutableLiveData<Boolean> = MutableLiveData()
    val nameError: LiveData<Boolean>
        get() = _nameError

    fun updateName(text: CharSequence) {
        _name.value = text.toString()
        Log.d("Yoshi", "${_name.value}")
        checkNameLength(_name.value.toString())
    }

    fun checkNameLength(name: String) {
        if (name.length < 2 || name.length > 12) {
            _nameError.value = true

        } else {
            _nameError.value = false
        }
    }
}