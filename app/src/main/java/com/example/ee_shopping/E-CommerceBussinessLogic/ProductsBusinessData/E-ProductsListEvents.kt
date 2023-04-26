//package com.example.ee_shopping.`E-CommerceBussinessLogic`.ProductsBusinessData
package com.example.ee_shopping.E

import com.example.ee_shopping.localReposity.LocalStoreProducts

sealed class ProductsListEvents{
    data class OnViewDetailProduct(val product: LocalStoreProducts):ProductsListEvents()

}