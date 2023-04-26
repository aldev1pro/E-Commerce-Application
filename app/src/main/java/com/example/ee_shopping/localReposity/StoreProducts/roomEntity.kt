package com.example.ee_shopping.localReposity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import coil.compose.AsyncImagePainter

//
//@Entity(tableName = TABLE_NAME)
//data class LocalStoreProducts(
//    @PrimaryKey(autoGenerate = true)
//    val id:Int? = null,
//   // @ColumnInfo(name = "productList")
//    val products: List<ProductInformation>
//)
@Entity
data class LocalStoreProducts(
    @PrimaryKey(autoGenerate = false)
    val id:Int? = null,
    val productName:String,
    val productPrice:Int,
    val productImage:Bitmap
)

//data class Products(
//   // val id: Int?,
//    val products: List<ProductInformation>
//)

/////**
// * Converts the network model to the local model for persisting
// * by the local data source
// */
//fun NetworkStoreProducts.asEntity() = LocalStoreProducts(
//   // id = id,
//    products = products
//)
//
///**
// * Converts the local model to the external model for use
// * by layers external to the data layer
// */
//fun LocalStoreProducts.asExternalModel() = Products(
//   // id = id,
//    products = products
//)