package com.example.manifesto

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
inline fun <reified V: ViewModel> Fragment.createViewModel(crossinline instance: () -> V): V {
    val factory = object : ViewModelProvider.Factory{
        override fun<T: ViewModel> create(modelClass: Class<T>): T {
            return instance() as T
        }
    }
    return ViewModelProvider(this,factory).get(V::class.java)
}