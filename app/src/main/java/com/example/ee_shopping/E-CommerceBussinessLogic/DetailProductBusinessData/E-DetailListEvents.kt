//package com.example.ee_shopping.`E-CommerceBussinessLogic`.DetailProductBusinessData
package com.example.ee_shopping.E

import com.example.ee_shopping.localReposity.LocalStoreProducts

sealed class DetailProductListEvents{
    object OnNavigateBackToProductScreen:DetailProductListEvents()
    object OnNavigateToAllItemsInCart:DetailProductListEvents()
    object AddProductToCart:DetailProductListEvents()
    data class SelectCartons(val noOfCartons:Int):DetailProductListEvents()

}