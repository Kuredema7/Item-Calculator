package com.example.item_calculator.data

import java.math.BigDecimal

data class Item(
    val id: Int = 0,
    val name: String = "",
    val quantity: Int = 0,
    val price: BigDecimal = BigDecimal("0.00")
)