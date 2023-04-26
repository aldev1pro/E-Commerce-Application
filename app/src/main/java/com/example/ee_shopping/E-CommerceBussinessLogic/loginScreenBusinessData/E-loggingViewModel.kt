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

class LoginViewModel(context: Application):ViewModel(){

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
    private var _loginChannel = Channel<ErrorResult<AuthResponseResource>>()
    val loginChannel = _loginChannel.receiveAsFlow()
    //channel for oneTimeEvents
    private var _oneTimeLogin = Channel<LoginUiEvents>()
    val oneTimeLogin = _oneTimeLogin.receiveAsFlow()

    fun executeLogin(event: LoginListEvents){
        when(event){
            is LoginListEvents.UserEmail->{
                //LoginListEvents.UserEmail(email = event.email)
                email = event.email
            }
            is LoginListEvents.UserPassword->{
                password = event.password
            }
            is LoginListEvents.LogIn->{

                signIn()

                fireAndForget(LoginUiEvents.NavigateToProductsScreen(Destination.ToProductsScreen.route))

            }
            is LoginListEvents.CreateAccount->{
                fireAndForget(LoginUiEvents.NavigateToProductsScreen(Destination.ToCreateAccountScreen.route))

            }
            is LoginListEvents.OnRetryLogin->{
                fireAndForget(LoginUiEvents.ShowSnackBar(
                    message = "",
                    action = "Retry"
                ))
            }
        }
    }

    var res:ErrorResult<AuthResponseResource>? by mutableStateOf(null)

    private fun signIn(){
     viewModelScope.launch {
            isLoading = true
          //  delay(2000)
         //should not be commented when am done
//         while(email.isBlank() || password.isBlank()){
//             isLoading = true
//             return@launch
//         }
          res = api.signIn(request = AuthSignIn(
             email = email,
             password = password
         ))
         _loginChannel.send(res!!)
            isLoading = false
        }
    }


    private fun fireAndForget(ui:LoginUiEvents) {
        viewModelScope.launch {
            _oneTimeLogin.send(ui)
            //should not be commented when am done
            if(email.isBlank() || password.isBlank()){
                _oneTimeLogin.send(ui)
//                isLoading = true
//                return@launch
            }
            //will be removed
            _oneTimeLogin.send(ui)
//            if(email.isBlank() || password.isBlank()){
//                _oneTimeLogin.send(ui)
//            }
            var get: HttpStatusCode
            _loginChannel.consume {
                this.receiveAsFlow().collect { event ->
                    when (event) {
                        is ErrorResult.Success -> {
                            get = event.data.statusCode
                            if (get.isSuccess()) {
                                _oneTimeLogin.send(ui)
                            }
                        }
                        else -> {

                        }
                    }
                }
            }

        }

    }
    fun addProductToRoom(product: LocalStoreProducts) {
        viewModelScope.launch {
            getLocalProductsRepository.addProducts(product)
        }
    }


}

class LoginFactory(private val context: Application): AbstractSavedStateViewModelFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return LoginViewModel(context = context) as T
    }

}

