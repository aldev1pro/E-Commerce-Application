package com.example.ee_shopping.userSerializeDataStore

import io.ktor.http.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInformationSignUp(
    val firstname:String = "",
    val lastname:String = "",
    val age:Int,
    val place:String = "",
    val ward:String = "",
    val telephone:String = "",
    val email:String = "",
    val password: String = "",
    val account:Int
)
@Serializable
data class AuthSignIn(
    val email: String,
    val password: String
)
@Serializable
data class AuthResponseResource(
    val token:String,
    @Contextual
    val statusCode: HttpStatusCode = HttpStatusCode.Accepted

)
@Serializable
data class ProductInformation(
    val productName:String,
    val imageUrl:String,
    val noOfCartons:String,
    val basePrice:String,
    val profit:String

)

@Serializable
data class Cart(var nameOfProduct:String, val noOfCartons:Int)

@Serializable
data class OrderProducts(
    val cart:List<Cart>,
)
@Serializable
data class MyString(
    val name:String,
)