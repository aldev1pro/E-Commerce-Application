package com.example.ee_shopping.E

import com.example.ee_shopping.localReposity.CartProducts.CartProducts
import com.example.ee_shopping.localReposity.LocalStoreProducts

sealed class CartItemsListEvents{
    object OnViewDetailProduct:CartItemsListEvents()
    object OnProceedToPayment:CartItemsListEvents()

}
