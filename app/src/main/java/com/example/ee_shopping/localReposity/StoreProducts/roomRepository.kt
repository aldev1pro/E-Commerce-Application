
package com.example.e_shopping.localRepository

import android.app.Application
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.localReposity.ProductDatabase


class ProductRepository(application: Application){
   // private val repository = ContactDatabase.databaseInitializer(context = application).getContactDoa()


    private val repository = ProductDatabase.databaseInitializer(context = application).productDao()

    val displayProducts = repository.displayProducts()

    suspend fun findProductId(productId:Int): LocalStoreProducts {
        return repository.findProductId(productId)
    }

    suspend fun addProducts(products:LocalStoreProducts){
        repository.addProducts(products = products)
    }


}
