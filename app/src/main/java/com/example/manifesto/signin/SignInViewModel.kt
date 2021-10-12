package com.example.manifesto.signin

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
    private val guestIdKey: Long? = null,
    private val edit: Boolean
) : ViewModel() {
    private var _fullName: MutableLiveData<String> = MutableLiveData()
    val fullName: LiveData<String>
        get() = _fullName

    private val _phoneNumber: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String>
        get() = _email

    private val _emergencyContactCumber: MutableLiveData<String> = MutableLiveData()
    val emergencyContactNumber: LiveData<String>
        get() = _emergencyContactCumber

    private val _emergencyContactName: MutableLiveData<String> = MutableLiveData()
    val emergencyContactName: LiveData<String>
        get() = _emergencyContactName

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

    private val _dataUpdated = MutableLiveData<Boolean>()
    val dataUpdated: LiveData<Boolean>
        get() = _dataUpdated

    var _buttonEnable: MutableLiveData<Boolean> = MutableLiveData()
    val buttonEnable: LiveData<Boolean>
        get() = _buttonEnable

    override fun onCleared() {
        super.onCleared()
    }

    private var guest = MutableLiveData<GuestEntity?>()

    private var editGuest = GuestEntity()

    init {
        if (edit) {
            initializeGuest()
        }
    }

    private fun initializeGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            getGuestFromDatabase()
            _dataUpdated.postValue(true)
        }
    }

    private suspend fun getGuestFromDatabase() {
        withContext(Dispatchers.IO) {
            editGuest = database.getGuest(guestIdKey)

        }
    }

    fun updateData() {
        viewModelScope.launch(Dispatchers.Main) {
            updateFields()
        }
        _dataUpdated.value = false
    }

    private fun updateFields() {
        _fullName.postValue(editGuest.guestName)
        _phoneNumber.postValue(editGuest.guestPhone)
        _email.postValue(editGuest.guestEmail)
        _emergencyContactCumber.postValue(editGuest.emergencyNumber)
        _emergencyContactName.postValue(editGuest.emergencyContactName)
    }

    fun newGuest() {
        val newGuest = GuestEntity()

        newGuest.guestName = _fullName.value.toString()
        newGuest.guestPhone = _phoneNumber.value.toString()
        newGuest.guestEmail = _email.value.toString()
        newGuest.emergencyNumber = _emergencyContactCumber.value.toString()
        newGuest.emergencyContactName = _emergencyContactName.value.toString()
        if (edit) {
            viewModelScope.launch(Dispatchers.IO) {
                newGuest.guestId = guestIdKey ?: 0
                Log.d("Yoshi", "$newGuest")
                update(newGuest)
                _navigateToMainScreen.postValue(true)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                insert(newGuest)
                _navigateToMainScreen.postValue(true)

            }
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

    private suspend fun update(guest: GuestEntity) {
        withContext(Dispatchers.IO) {
            database.update(guest)
        }
    }

    fun isEmpty() {
        _buttonEnable.value =
            _fullName.value != null && _phoneNumber.value != null && _email.value != null && _emergencyContactName.value != null && _emergencyContactCumber.value != null &&
                    _nameError.value == "" && _phoneError.value == "" && _emailError.value == "" && _emergencyContactNameError.value == "" && _emergencyContactNumberError.value == ""
    }

    // update functions
    fun updateName(text: CharSequence) {
        _fullName.value = text.toString()
        _nameError.value = checkNameLength(_fullName.value.toString())
        isEmpty()
    }

    fun updateNumber(text: CharSequence) {
        _phoneNumber.value = text.toString()
        _phoneError.value = checkPhoneLength(_phoneNumber.value.toString())
        isEmpty()
    }

    fun updateEmail(text: CharSequence) {
        _email.value = text.toString()
        _emailError.value = checkEmail(_email.value.toString())
        isEmpty()
    }

    fun updateEmergencyContactNumber(text: CharSequence) {
        _emergencyContactCumber.value = text.toString()
        _emergencyContactNumberError.value =
            checkPhoneLength(_emergencyContactCumber.value.toString())
        isEmpty()
    }

    fun updateEmergencyContactName(text: CharSequence) {
        _emergencyContactName.value = text.toString()
        _emergencyContactNameError.value = checkNameLength(_emergencyContactName.value.toString())
        isEmpty()
    }

    // Validations
    private fun checkNameLength(name: String): String {
        "[^A-Za-z0-9 ]".toRegex().apply {
            if (name.length < 2 || name.length > 12 || name.contains(this)) {
                return "Must be 2-12 character's long and have no special characters."
            }
        }
        return ""
    }

    private fun checkPhoneLength(phone: String): String {
        if (phone.length != 10) {
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