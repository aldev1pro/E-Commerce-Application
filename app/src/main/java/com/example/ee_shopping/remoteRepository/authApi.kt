package com.example.ee_shopping.remoteRepository

import com.example.ee_shopping.BASE_URL
import com.example.ee_shopping.ktorClientEngine.KtorClient
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.ee_shopping.preferencesDataStore.PreferencesTokenKey
import com.example.ee_shopping.preferencesDataStore.dataStore
import com.example.ee_shopping.userSerializeDataStore.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.AuthScheme.Bearer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

sealed class ErrorResult<out T>{
    data class Success<T>(val data:T): ErrorResult<T>()
    data class Error(val exception:Exception): ErrorResult<Nothing>()
}
interface AuthApi{
    suspend fun signUp(request:UserInformationSignUp):ErrorResult<List<ProductInformation>>
   // suspend fun signIn(request: AuthSignIn):ErrorResult<List<ProductInformation>>
    suspend fun signIn(request: AuthSignIn):ErrorResult<AuthResponseResource>

    suspend fun buyProducts(request:OrderProducts):ErrorResult<MyString>
}
class AuthApiImpl(private val context: Context):AuthApi{

    override suspend fun signUp(request: UserInformationSignUp): ErrorResult<List<ProductInformation>> {

            return try {
            val response:List<ProductInformation> = KtorClient.httpClient.post("$BASE_URL/signUp") {
                body = request
            }
            ErrorResult.Success(data = response)
        }catch (e:ClientRequestException){
            ErrorResult.Error(e)
        }catch (e:ServerResponseException){
            ErrorResult.Error(e)
        }catch (e:Exception){
            ErrorResult.Error(e)
        }
    }
    override suspend fun signIn(request: AuthSignIn): ErrorResult<AuthResponseResource> {
        return try{
            val response:AuthResponseResource = KtorClient.httpClient.post("$BASE_URL/signIn"){
                body = request
            }
            //PreferencesTokenKey.addData(context,response.token)
            ErrorResult.Success(data = response)
        }catch (e:ClientRequestException){
            ErrorResult.Error(e)
        }catch (e:ServerResponseException){
            ErrorResult.Error(e)
        }catch (e:Exception){
            ErrorResult.Error(e)
        }
    }
    override suspend fun buyProducts(request: OrderProducts):ErrorResult<MyString>{
        val getToken = PreferencesTokenKey.readData(context = context).first()

        return try{
            val response:MyString = KtorClient.httpClient.post("$BASE_URL/orderProducts"){
                header(key = "Authorization", value = "Bearer $getToken")
                body = request
            }
            ErrorResult.Success(data = response)
        }catch (e:ClientRequestException){
            ErrorResult.Error(e)
        }catch (e:ServerResponseException){
            ErrorResult.Error(e)
        }catch (e:Exception){
            ErrorResult.Error(e)
        }
    }

}


