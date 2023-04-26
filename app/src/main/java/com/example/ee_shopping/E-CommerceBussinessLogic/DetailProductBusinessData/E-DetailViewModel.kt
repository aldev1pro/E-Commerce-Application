//package com.example.ee_shopping.`E-CommerceBussinessLogic`.DetailProductBusinessData
package com.example.ee_shopping.E

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shopping.localRepository.ProductRepository
import com.example.ee_shopping.Destination
import com.example.ee_shopping.localReposity.CartProducts.CartProducts
import com.example.ee_shopping.localReposity.CartProducts.CartRoomRepository
import com.example.ee_shopping.localReposity.LocalStoreProducts
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailProductViewModel(application: Application,savedStateHandle: SavedStateHandle):ViewModel(){

    val getRepository = ProductRepository(application)
    val getCartRepository = CartRoomRepository(application)

    var _oneTimeDetail = Channel<DetailProductUserEvent>()
    val oneTimeDetail = _oneTimeDetail.receiveAsFlow()

    var basePrice by mutableStateOf(0)
    private set
    var productName by mutableStateOf("")
    private set
    var productImage:ImageBitmap? by mutableStateOf(null)
    var restoreproduct:LocalStoreProducts? = null

    var noOfItemsSelected by mutableStateOf(1)
    var showNoOfProductsInCart by mutableStateOf(0)

    val getProduct = savedStateHandle.get<Int>("productId")
    init{
        if(getProduct != -1){
            viewModelScope.launch {
                if(getProduct != null){
                    getRepository.findProductId(getProduct).let {
                        productName = it.productName
                        basePrice = it.productPrice
                        it.productImage.also { productImage = it.asImageBitmap() }

                        this@DetailProductViewModel.restoreproduct = it
                    }
                }
            }
        }
    }

    fun executeDetail(events:DetailProductListEvents){
        when(events){
            is DetailProductListEvents.OnNavigateBackToProductScreen->{
                fireAndForget(DetailProductUserEvent.NavigateToAnyScreen(Destination.ToProductsScreen.route))
            }
            is DetailProductListEvents.OnNavigateToAllItemsInCart->{
                fireAndForget(DetailProductUserEvent.NavigateToAnyScreen(Destination.ToProductsInCartScreen.route))
            }
            is DetailProductListEvents.SelectCartons->{
                when(events.noOfCartons){
                    1->{noOfItemsSelected = events.noOfCartons}
                    2->{noOfItemsSelected = events.noOfCartons}
                    3->{noOfItemsSelected = events.noOfCartons}
                    4->{noOfItemsSelected = events.noOfCartons}
                    5->{noOfItemsSelected = events.noOfCartons}
                    6->{noOfItemsSelected = events.noOfCartons}
                    7->{noOfItemsSelected = events.noOfCartons}
                    8->{noOfItemsSelected = events.noOfCartons}
                    9->{noOfItemsSelected = events.noOfCartons}
                    10->{noOfItemsSelected = events.noOfCartons}
                }
            }
            is DetailProductListEvents.AddProductToCart->{
                addProductsAndCartonsToCart()
                showNoOfProductsInCart++
            }
        }
    }

    private fun fireAndForget(ui:DetailProductUserEvent){
        viewModelScope.launch {
            _oneTimeDetail.send(ui)
        }
    }
    private fun addProductsAndCartonsToCart() {
        if (getProduct != -1) {
            viewModelScope.launch {
                if (getProduct != null) {
                    getRepository.findProductId(getProduct).let{
                        getCartRepository.addProductsToCart(
                            products = CartProducts(
                                id = it.id,
                                productName = productName,
                                productPrice = it.productPrice,
                                productImage = it.productImage,
                                productSize = noOfItemsSelected
                            )
                        )
                    }
                }
            }
        }
    }



}

class DetailProductFactory(private val context: Application): AbstractSavedStateViewModelFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return DetailProductViewModel(application = context,handle) as T
    }

}