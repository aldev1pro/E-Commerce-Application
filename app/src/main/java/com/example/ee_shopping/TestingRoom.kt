package com.example.ee_shopping

import android.app.Application
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_shopping.localRepository.ProductRepository

@Composable
fun testRoom(){
    val login:DRoom = viewModel(factory = DRoomFactory(LocalContext.current.applicationContext as Application))

    Text(text = "Finally figured it out" )
}
class DRoom(application: Application):ViewModel(){
    private val getLocalProductsRepository = ProductRepository(application = application)

}
class DRoomFactory(private val context: Application): AbstractSavedStateViewModelFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return DRoom(application = context) as T
    }

}