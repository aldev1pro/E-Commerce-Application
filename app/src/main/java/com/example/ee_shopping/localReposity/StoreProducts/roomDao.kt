package com.example.ee_shopping.localReposity

import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao{

    @Query("SELECT * FROM LocalStoreProducts")
    fun displayProducts(): Flow<List<LocalStoreProducts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: LocalStoreProducts)

    @Query("SELECT * FROM LocalStoreProducts WHERE id = :productId")
    suspend fun findProductId(productId:Int):LocalStoreProducts

//    @Delete
//    suspend fun deleteProducts()

}
//@Dao
//interface ProductDao{
//
//    @Query("SELECT * FROM productTable")
//    fun displayProducts(): Flow<List<NetworkStoreProducts>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addProducts(products: List<NetworkStoreProducts>)
//
//    @Query("SELECT * FROM TABLE_NAME WHERE id = :productId")
//    suspend fun findProductId(productId:Int):NetworkStoreProducts?
//
////    @Delete
////    suspend fun deleteProducts()
//
//}
