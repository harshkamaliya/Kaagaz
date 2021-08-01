package com.example.camerax.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.camerax.ChildItem
import com.example.camerax.R
import com.example.camerax.viewHolder.ChildImageViewHolder

class ChildImageAdapter(val itemList: List<ChildItem> ):RecyclerView.Adapter<ChildImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_child, parent, false)
        return ChildImageViewHolder(view)

    }

    override fun onBindViewHolder(holder: ChildImageViewHolder, position: Int) {
        val item = itemList[position]
        holder.setChildData(item)


    }

    override fun getItemCount(): Int {
       return itemList.size
    }

}