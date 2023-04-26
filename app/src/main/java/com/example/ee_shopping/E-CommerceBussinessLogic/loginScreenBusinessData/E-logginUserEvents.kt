package com.example.ee_shopping.E

sealed class LoginUiEvents{
    data class NavigateToProductsScreen(val route:String):LoginUiEvents()
    data class ShowSnackBar(val message:String, val action:String):LoginUiEvents()
    //data class NavigateToCreateNewAccount(val route:String):LoginUiEvents()
}
