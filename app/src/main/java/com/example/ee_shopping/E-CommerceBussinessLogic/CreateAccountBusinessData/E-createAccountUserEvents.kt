package com.example.ee_shopping.E

sealed class CreateAccountUserEvents{

data class NavigateToProductScreen(val route:String):CreateAccountUserEvents()

}
