package com.example.manifesto.signin

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manifesto.data.model.GuestDatabaseDao
import com.example.manifesto.data.model.GuestEntity
import kotlinx.coroutines.*

class SignInViewModel(
    val database: GuestDatabaseDao,
    application: Application
) : ViewModel() {
    val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
        get() = _name

    val _nameError: MutableLiveData<Boolean> = MutableLiveData()
    val nameError: LiveData<Boolean>
        get() = _nameError



    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private var guest = MutableLiveData<GuestEntity?>()

    init {
        initilizeGuest()
    }

    private fun initilizeGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            guest.postValue(null)
        }
    }

    fun newGuest() {
        if (false) {
            viewModelScope.launch(Dispatchers.IO) {
                val newGuest = GuestEntity()
                insert(newGuest)

            }
        } else {

        }
    }

    private suspend fun insert(guest: GuestEntity) {
        withContext(Dispatchers.IO) {
            database.insert(guest)
        }
    }

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