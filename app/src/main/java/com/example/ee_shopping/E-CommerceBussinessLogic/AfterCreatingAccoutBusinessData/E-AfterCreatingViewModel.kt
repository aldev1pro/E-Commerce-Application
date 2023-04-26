package com.example.ee_shopping.E

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.e_shopping.localRepository.ProductRepository
import com.example.ee_shopping.Destination
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.remoteRepository.AuthApiImpl
import com.example.ee_shopping.remoteRepository.ErrorResult
import com.example.ee_shopping.userSerializeDataStore.AuthResponseResource
import com.example.ee_shopping.userSerializeDataStore.AuthSignIn
import com.example.ee_shopping.userSerializeDataStore.ProductInformation
import io.ktor.http.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FinalAccountSetupViewModel(context: Application):ViewModel(){

    private val getLocalProductsRepository = ProductRepository(application = context)
    private val api = AuthApiImpl(context)

    val displayProducts = getLocalProductsRepository.displayProducts

    var isLoading by mutableStateOf(false)
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    //channel for apiRequest
    var _finalAccountChannel = Channel<ErrorResult<AuthResponseResource>>()
    val finalAccountChannel = _finalAccountChannel.receiveAsFlow()

    //channel for oneTimeEvents
    var _oneTimeFinalAccount = Channel<FinalAccountUserEvents>()
    val oneTimeFinalAccount = _oneTimeFinalAccount.receiveAsFlow()

    fun executeFinalAccount(event: FinalAccountSetupListEvents){
        when(event){
            is FinalAccountSetupListEvents.UserEmail->{
                //LoginListEvents.UserEmail(email = event.email)
                email = event.email
            }
            is FinalAccountSetupListEvents.UserPassword->{
                //LoginListEvents.UserPassword(password = event.password)
                password = event.password
            }
            is FinalAccountSetupListEvents.LogIn->{
                signIn()
                    fireAndForget(FinalAccountUserEvents.NavigateToProductsScreen(Destination.ToProductsScreen.route))

            }

        }
    }

    private fun signIn(){
        viewModelScope.launch {
            isLoading = true
            delay(2000)
            //should not be commented when am done
            while(email.isBlank() || password.isBlank()){
                isLoading = true
                return@launch
            }
            val result = api.signIn(request = AuthSignIn(
                email = email,
                password = password
            ))
            _finalAccountChannel.send(result)
            isLoading = false
        }
    }


    private fun fireAndForget(ui:FinalAccountUserEvents){
        viewModelScope.launch {
            //should not be commented when am done
            if(email.isBlank() || password.isBlank()){
                isLoading = true
                return@launch
            }
            if(email.isBlank() || password.isBlank()){
                _oneTimeFinalAccount.send(ui)
            }
            var get: HttpStatusCode
            _finalAccountChannel.consume {
                this.receiveAsFlow().collect { event ->
                    when (event) {
                        is ErrorResult.Success -> {
                            get = event.data.statusCode
                            if (get.isSuccess()) {
                                _oneTimeFinalAccount.send(ui)
                            }
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
    fun addProductToRoom(product: LocalStoreProducts){
        viewModelScope.launch {
            getLocalProductsRepository.addProducts(product)
        }
    }


}

class FinalAccountSetupFactory(private val context: Application): AbstractSavedStateViewModelFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return FinalAccountSetupViewModel(context = context) as T
    }

}


