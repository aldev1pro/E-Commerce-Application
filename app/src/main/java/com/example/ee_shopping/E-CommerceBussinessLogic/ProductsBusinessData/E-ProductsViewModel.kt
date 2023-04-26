//package com.example.ee_shopping.`E-CommerceBussinessLogic`.ProductsBusinessData
package com.example.ee_shopping.E

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shopping.localRepository.ProductRepository
import com.example.ee_shopping.Destination
import com.example.ee_shopping.E.FinalAccountSetupViewModel
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.remoteRepository.AuthApiImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application):ViewModel(){

    private val getLocalProductsRepository = ProductRepository(application = application)

    var productId by mutableStateOf(0)
    private set

    val displayProducts = getLocalProductsRepository.displayProducts

    var _oneTimeProducts = Channel<ProductsUiEvents>()
    val oneTimeProducts = _oneTimeProducts.receiveAsFlow()

    fun executeProducts(events: ProductsListEvents){
        when(events){
            is ProductsListEvents.OnViewDetailProduct->{
                productId = events.product.id!!
                fireAndForget(ProductsUiEvents.NavigateToDetailProduct(Destination.ToDetailProductScreen.route + "?productId=$productId"))
            }
        }
    }

    private fun fireAndForget(ui:ProductsUiEvents){
        viewModelScope.launch {
            _oneTimeProducts.send(ui)
        }
    }
    fun addProductToRoom(product: LocalStoreProducts){
        viewModelScope.launch {
            getLocalProductsRepository.addProducts(product)
        }
    }

}
class ProductFactory(private val context: Application): AbstractSavedStateViewModelFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return ProductViewModel(application = context) as T
    }

}