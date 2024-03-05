package com.example.item_calculator.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.item_calculator.data.Item
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import java.math.BigDecimal
import java.math.RoundingMode

class ItemViewModel : ViewModel() {

    var itemList: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())
        private set

    private fun getGrandTotal(): BigDecimal {
        return itemList.value.sumOf { item ->
            item.price.multiply(BigDecimal.valueOf(item.quantity.toLong()))
        }
    }

    fun getExpensePercentage(expense: String): BigDecimal {
        return BigDecimal(
            expense.toDouble().div(getGrandTotal().toDouble()).toString()
        ).setScale(3, RoundingMode.HALF_UP)
    }

    fun loadCsvDataFromInputStream(inputStream: InputStream) {
        viewModelScope.launch {
            val rows: List<Map<String, String>> = csvReader().readAllWithHeader(ips = inputStream)
            itemList.value = emptyList()
            rows.map { row ->
                itemList.update {
                    it + Item(
                        id = row["NO"]?.toInt() ?: 0,
                        name = row["ITEM"].toString(),
                        quantity = row["QTY"]?.toIntOrNull() ?: 0,
                        price = row["PRICE"]?.toBigDecimalOrNull() ?: (0.00).toBigDecimal()
                    )
                }
            }
        }
    }
}

fun Item.getOldTotalPerItem(): BigDecimal {
    return price.multiply(quantity.toBigDecimal()).setScale(3, RoundingMode.HALF_UP)
}

fun Item.getPriceWithExpense(expensePercentage: BigDecimal): BigDecimal {
    val expensePrice = price.multiply(expensePercentage)
    return price.add(expensePrice).setScale(2, RoundingMode.HALF_UP)
}

fun Item.getNewTotalPerItem(expensePercentage: BigDecimal): BigDecimal {
    val priceWithExpense = getPriceWithExpense(expensePercentage)
    return priceWithExpense.multiply(quantity.toBigDecimal()).setScale(2, RoundingMode.HALF_UP)
}
