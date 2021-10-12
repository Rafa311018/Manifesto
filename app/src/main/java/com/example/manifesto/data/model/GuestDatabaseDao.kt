package com.example.manifesto.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GuestDatabaseDao {

    @Insert
    fun insert(guest: GuestEntity)

    @Update
    fun update(guest: GuestEntity)

    //get a guest
    @Query("SELECT * FROM guest_manifesto_table WHERE guestId IN (:key)")
    fun getGuest(key: Long?): GuestEntity

    //show all guests
    @Query("SELECT * FROM guest_manifesto_table ORDER BY guestId DESC")
    fun getAllGuest(): List<GuestEntity>

    //Delete a guest
    @Query("DELETE FROM guest_manifesto_table WHERE guestId IN (:key)")
    fun deleteGuest(key: Long)
}