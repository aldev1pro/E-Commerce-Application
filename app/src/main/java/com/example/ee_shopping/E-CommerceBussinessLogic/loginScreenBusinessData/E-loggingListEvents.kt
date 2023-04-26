package com.example.ee_shopping.E

sealed class LoginListEvents{
    data class UserEmail(var email:String):LoginListEvents()
    data class UserPassword(var password:String):LoginListEvents()
    object LogIn:LoginListEvents()
    object CreateAccount:LoginListEvents()
    data class OnRetryLogin(var email: String,var password: String):LoginListEvents()
}
