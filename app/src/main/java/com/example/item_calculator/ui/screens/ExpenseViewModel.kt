package com.example.item_calculator.ui.screens

import androidx.lifecycle.ViewModel
import com.example.item_calculator.ui.components.DecimalFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExpenseViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()
    private val decimalFormatter = DecimalFormatter()

    fun onExpenseChange(newExpense: String) {
        _uiState.update { currentState ->
            currentState.copy(
                expense = decimalFormatter.cleanup(newExpense)
            )
        }
    }

}

data class ExpenseUiState(
    val expense: String = ""
)