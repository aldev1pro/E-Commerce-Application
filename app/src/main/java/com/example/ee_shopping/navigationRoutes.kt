package com.example.ee_shopping

sealed class Destination(val route:String){
    object ToLoginScreen:Destination(route = "login")
    object ToCreateAccountScreen:Destination(route = "createAccount")
    object ToProductsScreen:Destination(route = "products")
    object ToDetailProductScreen:Destination(route = "productDetail")
    object ToProductsInCartScreen:Destination(route = "cartProducts")
    object ToPaymentScreen:Destination(route = "pay")
    object ToFinalAccountSetupScreen:Destination(route = "finalAccountSetup")
    object ToPurchasedScreen:Destination(route = "purchased")
    object ToSplasScreen:Destination(route = "splash")
}