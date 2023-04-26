package com.example.ee_shopping.localReposity.CartProducts

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartProducts(
    @PrimaryKey(autoGenerate = false)
    val id:Int? = null,
    val productName:String,
    val productPrice:Int,
    val productImage: Bitmap,
    val productSize:Int
)