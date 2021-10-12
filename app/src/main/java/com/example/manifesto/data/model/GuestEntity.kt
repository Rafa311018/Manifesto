package com.example.manifesto.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest_manifesto_table")
data class GuestEntity(

    @PrimaryKey(autoGenerate = true)
    var guestId: Long = 0L,

    @ColumnInfo(name = "guest_name")
    var guestName: String = "",

    @ColumnInfo(name = "guest_phone")
    var guestPhone: String = "",

    @ColumnInfo(name = "guest_email")
    var guestEmail: String = "",

    @ColumnInfo(name = "emergency_number")
    var emergencyNumber: String = "",

    @ColumnInfo(name = "emergency_contact_name")
    var emergencyContactName: String = ""
)