package com.example.melave

import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

data class Washer(var nameLavador: String? = null,
                  var priceWashComplete: String? = null,
                  var priceWashMedium: String? = null,
                  var washerAdress: String? = null ): Serializable{
}


