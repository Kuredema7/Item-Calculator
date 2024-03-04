package com.example.item_calculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.item_calculator.ui.screens.DocumentDestination
import com.example.item_calculator.ui.screens.DocumentScreen
import com.example.item_calculator.ui.screens.ExpenseDestination
import com.example.item_calculator.ui.screens.ExpenseScreen
import com.example.item_calculator.ui.screens.ItemDestination
import com.example.item_calculator.ui.screens.ItemScreen
import com.example.item_calculator.ui.screens.ItemViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ItemNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val itemViewModel: ItemViewModel = viewModel()
    val itemList by itemViewModel.itemList.collectAsState()

    NavHost(
        navController = navController,
        startDestination = DocumentDestination.route,
        modifier = modifier
    ) {
        composable(
            route = DocumentDestination.route
        ) {
            DocumentScreen(
                navigateToExpense = { uri ->
                    val encodedUri = URLEncoder.encode(uri.toString(), StandardCharsets.UTF_8.toString())
                    navController.navigate("${ExpenseDestination.route}/${encodedUri}")
                }
            )
        }

        composable(
            route = ExpenseDestination.routeWithArgs,
            arguments = listOf(
                navArgument(
                    ExpenseDestination.DOCUMENT_URI_ARG
                ) {
                    type = NavType.StringType
                }
            )
        ) {

            it.arguments?.getString("documentUri")?.let { documentUri ->
                val context = LocalContext.current

                ExpenseScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onCalculate = { expenseValue ->
                        val documentUriAsInputStream =
                            context.contentResolver.openInputStream(documentUri.toUri())

                        if (documentUriAsInputStream != null) {
                            itemViewModel.loadCsvDataFromInputStream(documentUriAsInputStream)
                        }

                        navController.navigate("${ItemDestination.route}/$expenseValue")
                    }
                )
            }
        }

        composable(
            route = ItemDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDestination.EXPENSE_ARG) {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("expense")?.let { expenseValue ->
                ItemScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    items = itemList,
                    expense = itemViewModel.getExpensePercentage(expenseValue)
                )
            }
        }
    }
}