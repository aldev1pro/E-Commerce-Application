package com.example.ee_shopping.localReposity.CartProducts

import android.app.Application
import com.example.ee_shopping.localReposity.ProductDatabase

class CartRoomRepository(application: Application){
    // private val repository = ContactDatabase.databaseInitializer(context = application).getContactDoa()


    private val cartRepository = ProductDatabase.databaseInitializer(context = application).cartProductDao()

    val displayCartProducts = cartRepository.displayProducts()

    suspend fun findProductIdInCart(productId:Int): CartProducts {
        return cartRepository.findProductId(productId)
    }

    suspend fun addProductsToCart(products: CartProducts){
        cartRepository.addProducts(products = products)
    }


}