package com.example.melave

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(private val carList: ArrayList<Car>, var clickCar : ClickCar) : RecyclerView.Adapter<CarAdapter.MyViewHolder>(){

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

        holder.cardView?.setOnClickListener{

            clickCar.clickCar(currentItem)

        }
    }

    override fun getItemCount(): Int {

        return carList.size

    }

    interface ClickCar{

        fun clickCar(car: Car)

    }

    class MyViewHolder(carView : View) : RecyclerView.ViewHolder(carView){

        val carBrand : TextView? = carView.findViewById(R.id.carBrand)
        val carModel : TextView? = carView.findViewById(R.id.carModel)
        val carColor : TextView? = carView.findViewById(R.id.carColor)
        val carNumber : TextView? = carView.findViewById(R.id.carNumber)

        val cardView : CardView? = carView.findViewById(R.id.cardView_carItem)

    }

}