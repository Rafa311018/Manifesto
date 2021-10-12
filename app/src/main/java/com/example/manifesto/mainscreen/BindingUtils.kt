package com.example.manifesto.mainscreen

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.manifesto.data.model.GuestEntity

@BindingAdapter("FullName")
fun TextView.setFullName(item: GuestEntity?) {
    item?.let {
        text = item.guestName
    }
}