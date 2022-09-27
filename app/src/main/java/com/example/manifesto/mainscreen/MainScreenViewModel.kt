package com.example.manifesto.mainscreen

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.util.Log
import androidx.lifecycle.*
import com.example.manifesto.data.model.GuestDatabaseDao
import com.example.manifesto.data.model.GuestEntity
import kotlinx.coroutines.*
import java.sql.Struct

class MainScreenViewModel(
    val database: GuestDatabaseDao,
    application: Application
) : ViewModel() {

    private val _navigateToSignIn = MutableLiveData<Boolean>()
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn

    private val _deleteGuest = MutableLiveData<String>()
    val deleteGuest : LiveData<String>
    get() = _deleteGuest

//    private val _deleteUser = MutableLiveData<Boolean>()
//    val deleteUser: LiveData<Boolean>
//        get() = _deleteUser

    var guest: String? = null
    var guestIdG: Long = 0L

    var guests = MutableLiveData<List<GuestEntity>>()

    init {
        updateList()
    }

    private fun updateList() {
        viewModelScope.launch(Dispatchers.IO) {
            guests.postValue(database.getAllGuest())
        }
    }

    private val _navigateToEditGuest = MutableLiveData<Boolean>()
    val navigateToEditGuest: LiveData<Boolean>
    get() = _navigateToEditGuest

    fun onEditGuestClicked(id: Long){
        guestIdG = id
        _navigateToEditGuest.value = true
    }

    fun onEditGuestNavigated(){
        _navigateToEditGuest.value = false
    }

     override fun onCleared() {
        super.onCleared()
    }


    suspend fun clear(guestId: Long) {
        withContext(Dispatchers.IO) {
            database.deleteGuest(guestId)
        }
    }

    fun signInButton() {
        _navigateToSignIn.value = true
    }

    fun doneNavigating() {
        _navigateToSignIn.value = false
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteGuestFromDatabase()
        }
    }

    private suspend fun deleteGuestFromDatabase() {
        withContext(Dispatchers.IO) {
            database.deleteGuest(guestIdG)
            updateList()
        }
    }

    fun getGuestName(guestId: Long){
        guestIdG = guestId
        guest = null
        viewModelScope.launch {
            guest = getGuestFromDatabase(guestId)
            _deleteGuest.value = guest
        }
    }

    private suspend fun getGuestFromDatabase(guestId: Long): String? {
        var guestName = ""
        return withContext(Dispatchers.IO) {
            var guestData = database.getGuest(guestId)
            guestName = guestData?.guestName.toString()
            guestName
        }
    }
}

//class GuestViewModel(
//    val database: GuestDatabaseDao,
//    application: Application) : AndroidViewModel(application){
//
//    }