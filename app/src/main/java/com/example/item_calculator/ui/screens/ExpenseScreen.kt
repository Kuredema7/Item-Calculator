@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.item_calculator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.item_calculator.R
import com.example.item_calculator.ui.components.CustomTopAppBar
import com.example.item_calculator.ui.navigation.NavigationDestination
import com.example.item_calculator.ui.theme.ItemCalculatorTheme

object ExpenseDestination : NavigationDestination {
    override val route = "expense"
    override val titleRes = R.string.expense_screen_title
    const val DOCUMENT_URI_ARG = "documentUri"
    val routeWithArgs = "$route/{$DOCUMENT_URI_ARG}"
}

@Composable
fun ExpenseScreen(
    onNavigateUp: () -> Unit,
    data: String,
    expenseViewModel: ExpenseViewModel = viewModel()
) {
    val expenseUiState by expenseViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.expense_screen_title),
                onNavigationBack = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = data, modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)))
            ExpenseBody(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.medium_padding)),
                expenseUiState = expenseUiState,
                onValueChange = expenseViewModel::onExpenseChange
            )
        }
    }
}

@Composable
private fun ExpenseBody(
    modifier: Modifier = Modifier,
    expenseUiState: ExpenseUiState,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = expenseUiState.expense,
            onValueChange = onValueChange,
            label = { Text(text = stringResource(R.string.expense_label)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_padding)),
        )
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_padding)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = stringResource(R.string.document_picker))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseScreenPreview() {
    ItemCalculatorTheme {
        ExpenseScreen(
            onNavigateUp = {},
            data = "Document path"
        )
    }
}