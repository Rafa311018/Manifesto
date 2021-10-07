package com.example.manifesto.mainscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.manifesto.data.model.GuestDatabaseDao
import com.example.manifesto.data.model.GuestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainScreenViewModel(
    val database: GuestDatabaseDao,
    application: Application
) : ViewModel() {

    private val _navigateToSignIn = MutableLiveData<Boolean>()
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn

    private var guest = MutableLiveData<GuestEntity?>()

    private val guests = database.getAllGuest()

    suspend fun clear(guestId: Long) {
        withContext(Dispatchers.IO) {
            database.clear(guestId)
        }
    }

    fun signInButton() {
        _navigateToSignIn.value = true
    }

    fun doneNavigating() {
        _navigateToSignIn.value = false
    }


}

//class GuestViewModel(
//    val database: GuestDatabaseDao,
//    application: Application) : AndroidViewModel(application){
//
//    }