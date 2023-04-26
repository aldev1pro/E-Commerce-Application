//package com.example.ee_shopping.`E-CommerceBussinessLogic`.ProductsBusinessData
package com.example.ee_shopping.E


sealed class ProductsUiEvents{
    data class NavigateToDetailProduct(val route:String):ProductsUiEvents()
}