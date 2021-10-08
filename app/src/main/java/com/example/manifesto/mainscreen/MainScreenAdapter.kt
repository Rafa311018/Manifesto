package com.example.manifesto.mainscreen

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manifesto.R
import com.example.manifesto.TextItemViewHolder
import com.example.manifesto.data.model.GuestEntity

class MainScreenAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<GuestEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.d("Yoshi", "Data ${data}")
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.text_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.guestName

        Log.d("Yoshi", "${data.size}")
    }


}
