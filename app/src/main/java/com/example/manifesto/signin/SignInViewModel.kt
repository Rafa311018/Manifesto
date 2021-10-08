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
    val _fullname: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
        get() = _fullname

    val _phonenumber: MutableLiveData<String> = MutableLiveData()
    val phonenumber: LiveData<String>
        get() = _phonenumber

    val _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String>
        get() = _email

    val _emergencycontactnumber: MutableLiveData<String> = MutableLiveData()
    val emergencycontactnumber: LiveData<String>
        get() = _emergencycontactnumber

    val _emergencycontactname: MutableLiveData<String> = MutableLiveData()
    val emergencycontactname: LiveData<String>
        get() = _emergencycontactname

    private val _nameError: MutableLiveData<String> = MutableLiveData()
    val nameError: LiveData<String>
        get() = _nameError

    private val _phoneError: MutableLiveData<String> = MutableLiveData()
    val phoneError: LiveData<String>
        get() = _phoneError

    private val _emailError: MutableLiveData<String> = MutableLiveData()
    val emailError: LiveData<String>
        get() = _emailError

    private val _emergencyContactNumberError: MutableLiveData<String> = MutableLiveData()
    val emergencyContactNumberError: LiveData<String>
        get() = _emergencyContactNumberError

    private val _emergencyContactNameError: MutableLiveData<String> = MutableLiveData()
    val emergencyContactNameError: LiveData<String>
        get() = _emergencyContactNameError

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean>
        get() = _navigateToMainScreen

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private var guest = MutableLiveData<GuestEntity?>()

    init {
//        initializeGuest()
    }

//    private fun initializeGuest() {
//        viewModelScope.launch(Dispatchers.IO) {
//            guest.postValue(null)
//        }
//    }

    fun newGuest() {
        if (true) {
            viewModelScope.launch(Dispatchers.Main) {
                val newGuest = GuestEntity()

                newGuest.guestName = _fullname.value.toString()
                newGuest.guestPhone = _phonenumber.value.toString()
                newGuest.guestEmail = _email.value.toString()
                newGuest.emergencyNumber = _emergencycontactnumber.value.toString()
                newGuest.emergencyContactName = _emergencycontactname.value.toString()

                insert(newGuest)
                Log.d("Yoshi", "${newGuest.guestId}")
                _navigateToMainScreen.value = true

            }
        } else {

        }
    }

    fun doneNavigating() {
        _navigateToMainScreen.value = false
    }

    private suspend fun insert(guest: GuestEntity) {
        withContext(Dispatchers.IO) {
            database.insert(guest)
        }
    }

    // update functions
    fun updateName(text: CharSequence) {
        _fullname.value = text.toString()
        _nameError.value = checkNameLength(_fullname.value.toString())
    }

    fun updateNumber(text: CharSequence) {
        _phonenumber.value = text.toString()
        _phoneError.value = checkPhoneLength(_phonenumber.value.toString())
    }

    fun updateEmail(text: CharSequence) {
        _email.value = text.toString()
        _emailError.value = checkEmail(_email.value.toString())
    }

    fun updateEmergencyContactNumber(text: CharSequence) {
        _emergencycontactnumber.value = text.toString()
        _emergencyContactNumberError.value = checkPhoneLength(_emergencycontactnumber.value.toString())
    }

    fun updateEmergencyConntactName(text: CharSequence) {
        _emergencycontactname.value = text.toString()
        _emergencyContactNameError.value = checkNameLength(_emergencycontactname.value.toString())
    }

    // Validations
    private fun checkNameLength(name: String): String {
        if(name.length < 2 || name.length > 12)
        {
            return "Must be 2-12 character's long and have no special characters."
        }
        return ""
    }

    private fun checkPhoneLength(phone: String): String {
        if (phone.length != 10){
            return "Must enter 10 digit number."
        }
        return ""
    }

    private fun checkEmail(email: String): String {
        if ((!email.contains("@")) || (!email.contains("."))) {
            return "We do not recognize that as an email. Try again."
        }
        return ""
    }
}