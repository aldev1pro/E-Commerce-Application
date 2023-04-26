package com.example.ee_shopping.E

sealed class CartItemsUserEvents{
    data class NavigateToDetailProductScreen(val route:String):CartItemsUserEvents()
}
