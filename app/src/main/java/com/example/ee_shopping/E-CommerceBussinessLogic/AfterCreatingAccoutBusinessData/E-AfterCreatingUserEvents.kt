package com.example.ee_shopping.E

sealed class FinalAccountUserEvents{
    data class NavigateToProductsScreen(val route:String):FinalAccountUserEvents()
    //data class NavigateToCreateNewAccount(val route:String):LoginUiEvents()
}
