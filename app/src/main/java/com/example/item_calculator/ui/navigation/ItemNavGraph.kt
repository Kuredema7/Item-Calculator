package com.example.item_calculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.item_calculator.ui.screens.DocumentDestination
import com.example.item_calculator.ui.screens.DocumentScreen
import com.example.item_calculator.ui.screens.ExpenseDestination
import com.example.item_calculator.ui.screens.ExpenseScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ItemNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
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
                ExpenseScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    data = documentUri
                )
            }
        }
    }
}