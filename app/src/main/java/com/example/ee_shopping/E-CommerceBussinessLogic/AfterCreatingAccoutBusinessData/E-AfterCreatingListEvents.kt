package com.example.ee_shopping.E

sealed class FinalAccountSetupListEvents{
    data class UserEmail(var email:String):FinalAccountSetupListEvents()
    data class UserPassword(var password:String):FinalAccountSetupListEvents()
    object LogIn:FinalAccountSetupListEvents()
    //object CreateAccount:FinalAccountSetupListEvents()
}

