package com.example.ee_shopping.E

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.ee_shopping.Destination

//@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("NewApi")
@Composable
fun NavigationContainer(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.ToSplasScreen.route){
        composable(route = Destination.ToSplasScreen.route){
            EsplashScreen(nav = navController)
        }
        composable(route = Destination.ToLoginScreen.route){
            LoginScreen(nav = {navController.navigate(it.route)})
        }
        composable(route = Destination.ToFinalAccountSetupScreen.route){
            FinalAccountSetupScreen(nav = {navController.navigate(it.route)})
        }
        composable(route = Destination.ToProductsScreen.route){
            ProductScreen(nav =
            {navController.navigate(it.route)
           // {popUpTo(Destination.ToLoginScreen.route) { inclusive = false }}
            })
        }
        composable(route = Destination.ToCreateAccountScreen.route){
            CreateAccountScreen(nav = { navController.navigate(it.route) })
        }
        composable(route = Destination.ToDetailProductScreen.route + "?productId={productId}",
            arguments = listOf(navArgument(name = "productId"){
                type = NavType.IntType
                defaultValue = -1
            })
        ){
            DetailProductScreen(nav = {navController.navigate(it.route)
           // {popUpTo(Destination.ToProductsScreen.route) { inclusive = false }}
            })
        }
        composable(route = Destination.ToProductsInCartScreen.route){
            CartScreen(nav = {navController.navigate(it.route)})
        }
        composable(route = Destination.ToPurchasedScreen.route + "?name={name}",
            arguments = listOf(navArgument(name = "name"){
                type = NavType.StringType
                defaultValue = ""
            })
        ){
            PurchasedScreen()

        }
    }
}
