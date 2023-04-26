package com.example.ee_shopping.E

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ee_shopping.Destination
import com.example.ee_shopping.localReposity.CartProducts.CartRoomRepository
import com.example.ee_shopping.remoteRepository.AuthApiImpl
import com.example.ee_shopping.remoteRepository.ErrorResult
import com.example.ee_shopping.userSerializeDataStore.Cart
import com.example.ee_shopping.userSerializeDataStore.MyString
import com.example.ee_shopping.userSerializeDataStore.OrderProducts
import com.example.ee_shopping.userSerializeDataStore.ProductInformation
import io.ktor.http.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class CartItemsViewModel(context: Application):ViewModel(){

    private val api = AuthApiImpl(context)
    private val getCartRepository = CartRoomRepository(context)

    var name by mutableStateOf("")
    var surname by mutableStateOf("")


    var _cartItemsChannel = Channel<ErrorResult<MyString>>()
    val cartItemsChannel = _cartItemsChannel.receiveAsFlow()

    val displayCartItems = getCartRepository.displayCartProducts


    var _oneTimeCartItem = Channel<CartItemsUserEvents>()
    val oneTimeCartItem = _oneTimeCartItem.receiveAsFlow()



    fun executeCartItems(events: CartItemsListEvents){
        when(events){
            is CartItemsListEvents.OnViewDetailProduct->{
                fireAndForget(CartItemsUserEvents.NavigateToDetailProductScreen(Destination.ToDetailProductScreen.route))

            }
            is CartItemsListEvents.OnProceedToPayment->{
                viewModelScope.launch {
                    val get = find()
                    val result = api.buyProducts(request =
                    OrderProducts(cart = get))
                    Log.d("Fan",result.toString())
                    _cartItemsChannel.send(result)
                }

            }

        }

    }
    fun happy() {
        viewModelScope.launch {
            val c = getCartRepository.displayCartProducts.collect {
                for(i in it){
                    surname = i.productName
                }
            }
        }
    }
    var cart:MutableList<Cart> = mutableListOf()
    fun find():List<Cart>{
         viewModelScope.launch {
             getCartRepository.displayCartProducts.collect {
                 for(i in it){
                     val holder = Cart(nameOfProduct = i.productName, noOfCartons = i.productSize)
                     cart.add(holder)
                 }
             }
         }
         return cart

    }

    fun fireAndForget(ui:CartItemsUserEvents){
        viewModelScope.launch {
            var get: HttpStatusCode
            _cartItemsChannel.consume {
                this.receiveAsFlow().collect { event ->
                    when (event) {
                        is ErrorResult.Success -> {
                        //    get = event.data.name
//                            if (get.isSuccess()) {
//                                _oneTimeCartItem.send(ui)
//                            }
                        }
                        else -> {

                        }
                    }
                }
            }
          //  _oneTimeCartItem.send(ui)
        }
    }
}


class CartItemsFactory(private val context: Application): AbstractSavedStateViewModelFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return CartItemsViewModel(context = context) as T
    }

}

