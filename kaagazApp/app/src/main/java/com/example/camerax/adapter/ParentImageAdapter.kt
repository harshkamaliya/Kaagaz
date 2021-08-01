package com.example.camerax.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.camerax.R
import com.example.camerax.model.CameraEntity

class ParentImageAdapter(val itemList: List<CameraEntity>) :RecyclerView.Adapter<ParentImageAdapter.ParentImageViewHolders>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentImageViewHolders {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parent, parent, false)
        return ParentImageViewHolders(view)


    }

    override fun onBindViewHolder(holder: ParentImageViewHolders, position: Int) {
        val item = itemList[position]


        Glide.with(holder.image).load(item.path).into(holder.image)
        holder.testView.text=item.name



    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ParentImageViewHolders(itemView: View):RecyclerView.ViewHolder(itemView){
       val image = itemView.findViewById<ImageView>(R.id.ivImageView)
       val testView = itemView.findViewById<TextView>(R.id.parent_item_title)
    }
}