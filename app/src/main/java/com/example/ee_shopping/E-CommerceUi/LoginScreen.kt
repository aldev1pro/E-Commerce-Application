package com.example.ee_shopping.E

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.preferencesDataStore.PreferencesTokenKey
import com.example.ee_shopping.remoteRepository.ErrorResult
import com.example.ee_shopping.userSerializeDataStore.ProductInformation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun LoginScreen(nav:(LoginUiEvents.NavigateToProductsScreen)->Unit) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
        onDispose {}
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        val context = LocalContext.current.applicationContext
        val login: LoginViewModel = viewModel(factory = LoginFactory(context = context as Application))

        var image: List<ProductInformation>? by remember { mutableStateOf(null) }
        var getToken = ""
        LaunchedEffect(key1 = true) {
            login.loginChannel.collect { event ->
                when (event) {
                    is ErrorResult.Success -> {
                        Log.d("Riles", event.data.toString().toList().toString())
                       // PreferencesTokenKey.addData(context.applicationContext,data = event.data.token)
                       // getToken = PreferencesTokenKey.readData(context = context).first()
                       // Toast.makeText(context, event.data.toString().toList().toString(), Toast.LENGTH_SHORT).show()

                       // Toast.makeText(context,event.data.statusCode.toString(),Toast.LENGTH_SHORT).show()
                       // Log.d("David", event.data.statusCode.toString())


                        //was in use
                       // image = event.data.toList()
                    }
                    is ErrorResult.Error -> {
                        Toast.makeText(context, event.exception.message, Toast.LENGTH_LONG).show()

                    }
                }
            }
        }

        val scaffoldState = rememberScaffoldState()
        LaunchedEffect(key1 = Unit) {
            login.oneTimeLogin.collect { event ->
                when (event) {
                    is LoginUiEvents.NavigateToProductsScreen -> {
                        Log.d("Riles1", event.route)
                        while (login.isLoading) {
                            var counter = 0L
                            counter += 1000L
                            Log.d("hel", "$counter")
                            delay(counter)
                        }
                        nav(event)
                    }
                    is LoginUiEvents.ShowSnackBar->{
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action
                        )
                        if(result == SnackbarResult.ActionPerformed){
                           login.executeLogin(LoginListEvents.LogIn)
                        }
                    }

                }

            }

        }
        Text(text = "Welcome User,", color = Color.Black, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = "Login To Your Account", color = Color.Black, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.size(30.dp))

        //Email TextField
        TextField(
            value = login.email,
            onValueChange = { login.executeLogin(event = LoginListEvents.UserEmail(it)) },
            label = {Text(text = "Email")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)

                )

        //Password TextField
        TextField(
            value = login.password,
            onValueChange = { login.executeLogin(event = LoginListEvents.UserPassword(it)) },
            label = {Text(text = "Password")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
                )
        Spacer(modifier = Modifier.size(30.dp))

        //Login Button
        var checkStatus by remember { mutableStateOf(false) }
        checkStatus = !(login.email.isBlank()||login.password.isBlank())
        Button(onClick = {
            login.executeLogin(event = LoginListEvents.LogIn)
            if(login.email.isBlank() || login.password.isBlank()){
                Toast.makeText(context, "email or password can't be empty", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier.fillMaxWidth(0.80F).height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
           enabled = checkStatus
        ) {
            Text(text = "Login",color = Color.White, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(modifier = Modifier.size(20.dp))

        //CreateAccount Button
        Button(onClick = {
            login.executeLogin(event = LoginListEvents.CreateAccount)
        },
            modifier = Modifier.fillMaxWidth(0.80F).height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            border = BorderStroke(1.dp,Color.Black)
        ) {
            Text(text = "Create New Account", color = Color.Black, fontWeight = FontWeight.ExtraBold)
        }
        if (login.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


        var final: Bitmap? by remember { mutableStateOf(null) }


//        var id = 1
//            for (i in image.orEmpty()) {
//                LaunchedEffect(key1 = true) {
//                val imageLoader = ImageLoader(context)
//                val request = ImageRequest.Builder(context)
//                    .data(i.imageUrl)
//                    .build()
//                    val drawable = imageLoader.execute(request = request).drawable
//                    final = (drawable as BitmapDrawable).toBitmapOrNull()
//                    login.addProductToRoom(product = LocalStoreProducts(
//                    id = id,
//                    productName = i.productName,
//                    productImage = final!!,
//                    productPrice = i.basePrice.toInt()
//                ))
//                id+=1
//            }
//        }
//





//        val myData by login.displayProducts.collectAsState(initial = emptyList())
//
//        LazyColumn {
//            items(myData) {
//                Image(bitmap = it.productImage.asImageBitmap(),
//                    contentDescription = null,
//                    modifier = Modifier.size(100.dp))
//                Text(text = "${it.productName} and D${it.productPrice}")
//
//            }
//        }

    }
}















































//Need this image for testing
//        Image(
//            //  painter = rememberImagePainter(image, builder = {}),
//            painter = rememberImagePainter(image?.first()?.imageUrl, builder = {}),
//            contentDescription = null
//        )
//        LazyColumn {
//            items(image.orEmpty()) {
//                // items(test){
//                // Text(text = "${it.productName} and ${it.basePrice}")
//                //Image(painter = rememberImagePainter(it.imageUrl, builder = {}),contentDescription = null)
//                val p = rememberImagePainter(data = it.imageUrl, builder = {})
//
//                //val p = rememberImagePainter(data = it, builder = {})
//                Log.d("hel","$p")
//                Image(painter = p, contentDescription = null, modifier = Modifier.size(30.dp), )
//                Text(text = "${it.productName} and ${it.basePrice}")
//
//            }
//        }



//@Composable
//suspend fun getBitmap():Bitmap{
//    val context = LocalContext.current
//    val loading = ImageLoader(context)
//    val request = ImageRequest.Builder(context)
//        .data("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrBmDg5abY1gv0FWmf6n1mT9te5ydoJqig0tsYgnn7Xg&s")
//        .build()
//    val result = (loading.execute(request) as SuccessResult).drawable
//    return (result as BitmapDrawable).bitmap
//
//}

//I cut this from the main screen
//LaunchedEffect(key1 = Unit){
//            for(i in image.orEmpty()) {
//                val loading = ImageLoader(context)
//                val request = ImageRequest.Builder(context)
//                    .data(i.imageUrl)
//                    .build()
//                val result = (loading.execute(request) as SuccessResult).drawable
//                 final = (result as BitmapDrawable).bitmap
//                Log.d("yes","$final")
//
//             val a = login.addProductToRoom(product = LocalStoreProducts(
//                    id = 0,
//                    productName = i.productName,
//                    productImage = final!!,
//                    productPrice = i.basePrice.toInt()
//                ))
//                Log.d("no", a.toString())
//
//            }
//
//        }
















//data class People(val name:String, val place:String)
//val people = listOf(
//    People("Mike","Yundum"),
//    People("Joe","Farato"),
//    People("Fatou","Yundum"),
//    People("Mike","Busumbala"),
//    People("Joe","Yundum"),
//    People("Lamin","Tokyo"),
//    People("Kebbeh","Yundum"),
//    People("Isatou","Mandinaring"),
//    People("Mike","Brikama"),
//    People("Michael","Yundum"),
//    People("Barton","Burusubi"),
//    People("Lizy","Yundum"),
//    People("Mike","Yundum"),
//    People("Obi","Amsterdam"),
//    )
//val nol = people
//        val ls = listOf(People(name = "Mike", place = "Yundum"),People(name = "Aja","Farato"),People(name = "Binta","Busumbala"))
//        val test = listOf("http://192.168.0.119:8080/images/Cerelack.jpg","http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg","http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg","http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg","http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg",
//        "http://192.168.0.119:8080/images/Cerelack.jpg","http://192.168.0.119:8080/images/Cerelack.jpg",
//            "http://192.168.0.119:8080/images/Cerelack.jpg")
















































/*

 val posts = produceState(
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

 */