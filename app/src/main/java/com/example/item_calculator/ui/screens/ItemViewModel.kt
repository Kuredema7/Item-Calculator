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
            items.value = emptyList()
            rows.map { row ->
                items.update {
                    it + Item(
                        id = row["NO"]?.toInt() ?: 0,
                        name = row["ITEM"].toString(),
                        quantity = row["QTY"].toString(),
                        price = row["PRICE"].toString()
                    )
                }
            }
        }
    }
}