package com.example.melave


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ValueEventListener

class CarAdapter(
    private val carList: ArrayList<Car>) : RecyclerView.Adapter<CarAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val carView = LayoutInflater.from(parent.context).inflate(R.layout.cars_item,
                parent, false)
        return MyViewHolder(carView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = carList[position]

        holder.carBrand?.text = currentItem.carBrand
        holder.carModel?.text = currentItem.carModel
        holder.carColor?.text = currentItem.carColor
        holder.carNumber?.text = currentItem.carNumber
        holder.carSituation?.text = currentItem.carSituation

        holder.itemView.setOnClickListener {
            val intent = Intent(
                it.context, CarDetailsActivity::class.java
            )
            intent.putExtra("carDetails", currentItem)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return carList.size

    }

     class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val carBrand: TextView? = itemView.findViewById(R.id.carBrand)
        val carModel: TextView? = itemView.findViewById(R.id.carModel)
        val carColor: TextView? = itemView.findViewById(R.id.carColor)
        val carNumber: TextView? = itemView.findViewById(R.id.carNumber)
         val carSituation: TextView? = itemView.findViewById(R.id.details_car_situation)

    }

    }



