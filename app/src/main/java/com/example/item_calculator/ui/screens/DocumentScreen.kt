@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.item_calculator.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.item_calculator.R
import com.example.item_calculator.ui.components.CustomTopAppBar
import com.example.item_calculator.ui.navigation.NavigationDestination
import com.example.item_calculator.ui.theme.ItemCalculatorTheme

/*
context.contentResolver.openInputStream(uri)
                ?.let {
                    csvReader().open(it) {
                        readAllWithHeaderAsSequence().forEach { row: Map<String, String> ->

                        }
                    }
                }
 */

object DocumentDestination : NavigationDestination {
    override val route = "document"
    override val titleRes = R.string.document_upload
}

@Composable
fun DocumentScreen(
    navigateToExpense: (Uri) -> Unit
) {
    val context = LocalContext.current
    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            navigateToExpense(uri)
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.document_upload),
                onNavigationBack = { },
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        DocumentBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.medium_padding)),
            onClick = {
                filePicker.launch(arrayOf("text/comma-separated-values"))
            }
        )
    }
}

@Composable
private fun DocumentBody(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onClick) {
            Text(text = stringResource(R.string.document_picker))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentScreenPreview() {
    ItemCalculatorTheme {
        DocumentScreen(
            navigateToExpense = {}
        )
    }
}