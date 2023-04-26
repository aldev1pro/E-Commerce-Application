package com.example.ee_shopping.localReposity.CartProducts

import androidx.room.*
import com.example.ee_shopping.localReposity.LocalStoreProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface CartRoomDao {

    @Query("SELECT * FROM CartProducts")
    fun displayProducts(): Flow<List<CartProducts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: CartProducts)

    @Query("SELECT * FROM CartProducts WHERE id = :productId")
    suspend fun findProductId(productId: Int): CartProducts

    @Query("DELETE FROM CartProducts")
    suspend fun deleteProducts()

}