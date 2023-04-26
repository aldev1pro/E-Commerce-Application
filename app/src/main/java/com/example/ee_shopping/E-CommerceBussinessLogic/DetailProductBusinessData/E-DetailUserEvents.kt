//package com.example.ee_shopping.`E-CommerceBussinessLogic`.DetailProductBusinessData
package com.example.ee_shopping.E

sealed class DetailProductUserEvent{

    data class NavigateToAnyScreen(val route:String):DetailProductUserEvent()
}