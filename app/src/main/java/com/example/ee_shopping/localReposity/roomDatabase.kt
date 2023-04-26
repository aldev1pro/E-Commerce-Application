package com.example.ee_shopping.localReposity

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ee_shopping.localReposity.CartProducts.CartProducts
import com.example.ee_shopping.localReposity.CartProducts.CartRoomDao


@Database(entities = [LocalStoreProducts::class,CartProducts::class], version = 1, exportSchema = false)
@TypeConverters(ConverterImpl::class)
abstract class ProductDatabase:RoomDatabase(){
    abstract fun productDao():ProductDao
    abstract fun cartProductDao():CartRoomDao

    companion object{

        @Volatile
        private var DATABASE_INSTANCE:ProductDatabase? = null

        fun databaseInitializer(context: Context):ProductDatabase{
            return DATABASE_INSTANCE?: synchronized(this){
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "proDatabase"
                ).build()
                DATABASE_INSTANCE = inst
                inst
            }

        }

    }
//    companion object{
//
//        @Volatile
//        private var DATABASE_INSTANCE:ProductDatabase? = null
//
//        fun databaseInitializer(context: Application):ProductDatabase{
//            return DATABASE_INSTANCE?: synchronized(this){
//                val inst = Room.databaseBuilder(
//                    context.applicationContext,
//                    ProductDatabase::class.java,
//                    "productDatabase"
//                ).build()
//                DATABASE_INSTANCE = inst
//                inst
//            }
//
//        }
//
//    }



}

