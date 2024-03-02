package com.example.item_calculator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.item_calculator.ui.navigation.ItemNavHost

@Composable
fun ItemApp(
    navHostController: NavHostController = rememberNavController()
) {
    ItemNavHost(navController = navHostController)
}