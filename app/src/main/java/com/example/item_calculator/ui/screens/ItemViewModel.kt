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

    var items: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())
        private set

    fun loadCsvDataFromInputStream(inputStream: InputStream){
        viewModelScope.launch {
            val rows: List<Map<String,String>> = csvReader().readAllWithHeader(ips = inputStream)
            rows.map { row ->
                items.update {
                    it + Item(
                        id = row["NO"]?.toIntOrNull(),
                        name = row["ITEM"],
                        quantity = row["QTY"]?.toIntOrNull(),
                        price = row["PRICE"]?.toDoubleOrNull()
                    )
                }
            }
        }
    }
    
}