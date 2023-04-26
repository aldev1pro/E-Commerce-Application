package com.example.ee_shopping.E

sealed class CreateAccountListEvents{
    data class OnFirstNameChange(val firstname:String):CreateAccountListEvents()
    data class OnLastNameChange(val lastname:String):CreateAccountListEvents()
    data class OnPlaceChange(val place:String):CreateAccountListEvents()
    data class OnWardChange(val ward:String):CreateAccountListEvents()
    data class OnAgeChange(val age:String):CreateAccountListEvents()
    data class OnTelephoneChange(val telephone:String):CreateAccountListEvents()
    data class OnEmailChange(val email:String):CreateAccountListEvents()
    data class OnPasswordChange(val password:String):CreateAccountListEvents()
    data class OnAccountChange(val account:String):CreateAccountListEvents()
    object CreateAccount:CreateAccountListEvents()

}
