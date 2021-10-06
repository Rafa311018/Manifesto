package com.example.manifesto.mainscreen

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.manifesto.R
import com.example.manifesto.data.model.GuessEntity

class MainScreenViewModel : ViewModel() {

    private val _navigateToSignIn = MutableLiveData<Boolean>()
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn

    fun signInButton() {
        _navigateToSignIn.value = true
    }

    fun doneNavigating() {
        _navigateToSignIn.value = false
    }


}