package com.example.manifesto.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guess_manifesto")
data class GuessEntity(

    @PrimaryKey(autoGenerate = true)
    var guessId: Long = 0L,

    @ColumnInfo(name = "guess_name")
    var guessPhone: String = "",

    @ColumnInfo(name = "guess_email")
    var guessEmail: String = "",

    @ColumnInfo(name = "emergency_number")
    var emergencyNumber: String = "",

    @ColumnInfo(name = "emergency_contact_name")
    var emergencyContactName: String = ""
)