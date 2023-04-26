package com.example.ee_shopping.E

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.e_shopping.localRepository.ProductRepository
import com.example.ee_shopping.Destination
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.remoteRepository.AuthApiImpl
import com.example.ee_shopping.remoteRepository.ErrorResult
import com.example.ee_shopping.userSerializeDataStore.ProductInformation
import com.example.ee_shopping.userSerializeDataStore.UserInformationSignUp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel(context: Application):ViewModel(){

    private val api = AuthApiImpl(context)
    private val getLocalProductsRepository = ProductRepository(application = context)


    var IsLoading:Boolean? by mutableStateOf(null)
    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var age by mutableStateOf("")
    var place by mutableStateOf("")
    var ward by mutableStateOf("")
    var telephone by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var account by mutableStateOf("")

    private var _createAccountChannel = Channel<ErrorResult<List<ProductInformation>>>()
    val createAccountChannel = _createAccountChannel.receiveAsFlow()

    private val _oneTimeCreateAccount = Channel<CreateAccountUserEvents>()
    val oneTimeCreateAccount = _oneTimeCreateAccount.receiveAsFlow()

    fun executeCreateAccount(events: CreateAccountListEvents){
        when(events){
            is CreateAccountListEvents.OnFirstNameChange->{
                firstname = events.firstname
            }
            is CreateAccountListEvents.OnLastNameChange->{
                lastname = events.lastname
            }
            is CreateAccountListEvents.OnAgeChange->{
                try {
                    age = events.age
                }catch (e:Exception){
                    println("age cannot be empty $e")
                }
            }
            is CreateAccountListEvents.OnPlaceChange->{
                place = events.place
            }
            is CreateAccountListEvents.OnWardChange->{
                ward = events.ward
            }
            is CreateAccountListEvents.OnTelephoneChange->{
                telephone = events.telephone
            }
            is CreateAccountListEvents.OnEmailChange->{
                email = events.email
            }
            is CreateAccountListEvents.OnPasswordChange->{
                password = events.password
            }
            is CreateAccountListEvents.OnAccountChange->{
                account = events.account
            }
            is CreateAccountListEvents.CreateAccount->{
                createAccount()
                fireAndForget(CreateAccountUserEvents.NavigateToProductScreen(route = Destination.ToFinalAccountSetupScreen.route))
            }

        }
    }
    private fun createAccount(){
        viewModelScope.launch {
            IsLoading = true
            delay(2000)
            //should not be commented when am done
            while(firstname.isBlank() || lastname.isBlank() || age.isBlank() ||
                place.isBlank() || ward.isBlank() || email.isBlank() || password.isBlank() ||
                account.isBlank()
            ){
                IsLoading = true
                return@launch
            }
            val result = api.signUp(request = UserInformationSignUp(
                firstname = firstname,
                lastname = lastname,
                age = age.toInt(),
                place = place,
                ward = ward,
                email = email,
                password = password,
                account = account.toInt()
            ))
            IsLoading = false

            _createAccountChannel.send(result)
        }
    }
    private fun fireAndForget(ui:CreateAccountUserEvents){
        viewModelScope.launch {
            //should not be commented when am done
//            if(firstname.isBlank() || lastname.isBlank() || age.isBlank() ||
//                place.isBlank() || ward.isBlank() || email.isBlank() || password.isBlank() ||
//                account.isBlank()
//            ){
//                IsLoading = true
//                return@launch
//            }
            _oneTimeCreateAccount.send(ui)
        }
    }
    fun addProductToRoom(product: LocalStoreProducts) {
        viewModelScope.launch {
            getLocalProductsRepository.addProducts(product)
        }
    }

}

class CreateAccountFactory(private val context: Application): AbstractSavedStateViewModelFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return CreateAccountViewModel(context = context) as T
    }

}