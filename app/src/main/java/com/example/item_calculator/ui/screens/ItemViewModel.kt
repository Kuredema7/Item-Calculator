package com.example.item_calculator.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.item_calculator.data.Item
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream

class ItemViewModel: ViewModel() {

    var itemList: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())
        private set

    fun getGrandTotal(): Double {
        return itemList.value.sumOf { item -> item.quantity.times(item.price) }
    }

    fun getExpensePercentage(expense: String): Double {
        return String.format("%.2f", (expense.toDouble().div(getGrandTotal()))).toDouble()
    }

    fun loadCsvDataFromInputStream(inputStream: InputStream){
        viewModelScope.launch {
            val rows: List<Map<String,String>> = csvReader().readAllWithHeader(ips = inputStream)
            itemList.value = emptyList()
            rows.map { row ->
                itemList.update {
                    it + Item(
                        id = row["NO"]?.toInt() ?: 0,
                        name = row["ITEM"].toString(),
                        quantity = row["QTY"]?.toIntOrNull() ?: 0,
                        price = row["PRICE"]?.toDoubleOrNull() ?: 0.00
                    )
                }
            }
        }
    }
}

fun Item.getTotalPerItem(): Double {
    return String.format("%.2f", (quantity.times(price))).toDouble()
}