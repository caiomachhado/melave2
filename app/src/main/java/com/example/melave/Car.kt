package com.example.melave

import java.io.Serializable

data class Car(var carBrand: String? = null,
               var carModel: String? = null,
               var carColor: String? = null,
               var carNumber: String? = null
): Serializable
