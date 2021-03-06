package com.example.melave

import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WasherAdapter(private val washerList: ArrayList<Washer>) : RecyclerView.Adapter<WasherAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.washer_available,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = washerList[position]

        holder.nameLavador.text = currentItem.nameLavador
        holder.washerAdress.text = currentItem.washerAdress
        holder.priceWashComplete.text = currentItem.priceWashComplete
        holder.priceWashMedium.text = currentItem.priceWashMedium

        holder.itemView.setOnClickListener {
            val intent = Intent(
                it.context, WasherDetails::class.java
            )
            intent.putExtra("washerDetails", currentItem)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return washerList.size

    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nameLavador : TextView = itemView.findViewById(R.id.washer_name)
        val washerAdress : TextView = itemView.findViewById(R.id.washer_adress)
        val priceWashComplete : TextView = itemView.findViewById(R.id.priceCompleteWash)
        val priceWashMedium : TextView = itemView.findViewById(R.id.priceMediumWash)

    }

}


