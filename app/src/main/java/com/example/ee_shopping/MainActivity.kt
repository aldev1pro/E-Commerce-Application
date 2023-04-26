package com.example.ee_shopping

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.ee_shopping.E.NavigationContainer
import com.example.ee_shopping.remoteRepository.ErrorResult
import com.example.ee_shopping.ui.theme.EEShoppingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import java.io.IOException

class MainActivity : ComponentActivity() {
   // @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EEShoppingTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    //Greeting("Android")
                   // Deciding()

                    NavigationContainer()
                   // Image()
                   // testRoom()

                }
            }
        }
    }
}
@Composable
fun Image(){
    //val imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrBmDg5abY1gv0FWmf6n1mT9te5ydoJqig0tsYgnn7Xg&s"
    //val imageUrl =     "http://127.0.0.1.8080/images/biscuit.jpg"
    //val imageUrl = "http://127.0.0.1:8080/images/biscuit.jpg"
    val imageUrl = "http://192.168.0.119:8080/images/Cerelack.jpg"

    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {}
    )
    val painterState = painter.state
    if(painterState is AsyncImagePainter.State.Loading){
        CircularProgressIndicator()
    }
    androidx.compose.foundation.Image(painter = painter,contentDescription = null)
//    val a = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(data = null)
//        .apply(block = fun ImageRequest.Builder.() {
//
//        }).build())

//    val emit =  produceState(0){
//       // value = 2
//        var a = 0
//        for(i in 1..10){
//            delay(1000)
//            a += i
//        }
//        this.value = a
//
//    }
//    Text(text = emit.value.toString())

}



//val posts = produceState(
//                        initialValue = emptyList<Avater>(),
//                        producer = {
//                            value = service.getPost()
//                        }
//
//                    )
//
//
//                    LazyColumn{
//                        items(posts.value){
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(16.dp)
//                            ){
//                                Text(text = "Testing")
//                                Spacer(modifier = Modifier.height(2.dp))
//                                Text(text = it.name)
//                                Spacer(modifier = Modifier.height(2.dp))
//                                val painter =
//                                    rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
//                                        .data(data = it.imageUrl).apply(block = fun ImageRequest.Builder.() {
//                                            error(R.drawable.ic_launcher_background)
//                                        }).build())
//
//                                Image(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(275.dp),
//                                    contentScale = ContentScale.Crop,
//                                    contentDescription = "Coil Image",
//                                    painter = painter
//                                )
//                                //Text(text = it.title)
//                            }
//                        }
//                    }
































































































//@Composable
//fun Deciding() {
//    val nol: Shopping = viewModel(factory = ContainViewModelFactory(
//        context = LocalContext.current.applicationContext as Application))
//
//    val context = LocalContext.current
//    LaunchedEffect(key1 = true) {
//        nol.oneTime.collect { result ->
//            when (result) {
//                is ErrorResult.Success -> {
//                    Toast.makeText(context,
//                        "RobostHttpResponse:${result.data}",
//                        Toast.LENGTH_SHORT).show()
//                }
//                is ErrorResult.Error -> {
//                    Toast.makeText(
//                        context,
//                        "Bad Request, Try again",
////                        when (result.exception.message) {
////                            400 -> "${result.code.status.value}.bad request:\n Error on client's site"
////                            401 -> " ${result.code.status.value}.Unauthorized:\nInvalid credentials"
////                            403 -> "${result.code.status.value}.Access to that resource is forbidden:\n Invalid credentials to access this resource"
////                            404 -> "${result.code.status.value}.The requested resource was not found:\n Requested resource does not exit"
////                            409 -> "${result.code.status.value}.Conflict.  conflict with relevant resource"
////                            500 -> "${result.code.status.value}.Internal server error:\n  Server cannot respond"
////                            503 -> "${result.code.status.value}.Server could not handle request right now"
////                            else -> {
////                                "General Error occurred"
////                            }
////                        },
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//            }
//
//        }
//    }
//
//    var firstname by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var lastname by remember{ mutableStateOf("") }
//    var age by remember{ mutableStateOf(0) }
//    var place by remember { mutableStateOf("") }
//    var ward by remember { mutableStateOf("") }
//    var telephone by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var account by remember { mutableStateOf(0) }
//
//    val scope = rememberCoroutineScope()
//
//    Column {
//        Text("Username")
//        TextField(
//            value = nol.firstname,
//            onValueChange = { nol.firstname = it }
//        )
//        Text("Password")
//        TextField(
//            value = nol.password,
//            onValueChange = { nol.password = it }
//        )
//
//        Button(
//            onClick = {
//                nol.handleEvents(UiState.SignUp)
//
//            }
//        ) { Text("SignUp") }
//        Text("SingIn")
//        TextField(
//            value = nol.firstname,
//            onValueChange = { nol.firstname = it }
//        )
//        Text("Password")
//        TextField(
//            value = nol.password,
//            onValueChange = { nol.password = it }
//        )
//        Button(
//            onClick = {
//                nol.handleEvents(UiState.SignIn)
//
//            }) {
//            Text("LogIn")
//        }
//
//
//
//
//    }
//
//
//}
//
//


























































