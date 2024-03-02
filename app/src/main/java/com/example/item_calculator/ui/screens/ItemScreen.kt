package com.example.item_calculator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.item_calculator.R
import com.example.item_calculator.data.Item
import com.example.item_calculator.ui.components.CustomTopAppBar
import com.example.item_calculator.ui.navigation.NavigationDestination
import com.example.item_calculator.ui.theme.ItemCalculatorTheme

object ItemDestination : NavigationDestination {
    override val route = "item"
    override val titleRes = R.string.item_screen_title
    //const val DOCUMENT_URI_ARG = "documentUri"
    //val routeWithArgs = "$route/{$DOCUMENT_URI_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(
    onNavigateUp: () -> Unit,
    items: List<Item>
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.item_screen_title),
                onNavigationBack = onNavigateUp
            )
        }
    ) { innerPadding ->
        ItemList(
            items = items,
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.medium_padding))
        )
    }
}

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    items: List<Item>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items) { item ->
            ItemCard(
                item = item,
                modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
            )
        }
    }
}

@Composable
private fun ItemCard(
    modifier: Modifier = Modifier,
    item: Item
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_padding))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.item_label))
                Text(text = item.name)
            }
            ItemRow(
                modifier = Modifier.fillMaxWidth(),
                quantity = item.quantity,
                price = item.price,
                total = item.quantity.toDouble().times(item.price.toDouble())
            )
        }
    }
}

@Composable
private fun ItemRow(
    modifier: Modifier = Modifier,
    quantity: String,
    price: String,
    total: Double
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_padding)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.quantity_label))
                Text(text = quantity)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.price_label))
                Text(text = price)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.total_label))
                Text(text = total.toString())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemRowPreview() {
    ItemCalculatorTheme {
        ItemRow(
            quantity = "38",
            price = "47",
            total = (38 * 47).toDouble()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemCardPreview() {
    val item = Item(
        id = 0,
        name = "Paindol",
        quantity = "38",
        price = "90"
    )
    ItemCalculatorTheme {
        ItemCard(
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)),
            item = item
        )
    }
}