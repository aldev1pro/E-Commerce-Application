package com.example.ee_shopping.E

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.remoteRepository.ErrorResult
import com.example.ee_shopping.userSerializeDataStore.ProductInformation
import kotlinx.coroutines.delay

@Composable
fun CreateAccountScreen(nav:(CreateAccountUserEvents.NavigateToProductScreen)->Unit) {

    val context = LocalContext.current.applicationContext
    val createAccount: CreateAccountViewModel =
        viewModel(factory = CreateAccountFactory(context as Application))

    var image: List<ProductInformation>? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = true) {
        createAccount.createAccountChannel.collect { event ->
            when (event) {
                is ErrorResult.Success -> {
                   // Toast.makeText(context, event.data.toString(), Toast.LENGTH_LONG).show()
                    //image = listOf(event.data)
                     image = event.data.toList()

                }
                is ErrorResult.Error -> {
                    Toast.makeText(context, event.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        createAccount.oneTimeCreateAccount.collect { event ->
            when (event) {
                is CreateAccountUserEvents.NavigateToProductScreen -> {
                    while (createAccount.IsLoading == true) {
                        var counter = 0L
                        counter += 1000L
                        Log.d("hel", "$counter")
                        delay(counter)
                    }
                    nav(event)
                }
            }
        }
    }
    val scroll = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scroll)
    ) {

        //Create Account
        Spacer(modifier = Modifier.size(30.dp))
        Text(text = "Create Account", color = Color.Black, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.size(30.dp))
        //firstName

        TextField(
            value = createAccount.firstname,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnFirstNameChange(
                        it
                    )
                )
            },
            label = { Text(text = "firstname") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)

        )

        //lastName
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.lastname,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnLastNameChange(
                        it
                    )
                )
            },
            label = { Text(text = "lastname") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )

        //age
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.age,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnAgeChange(
                        it
                    )
                )
            },
            label = { Text(text = "age") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        //place
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.place,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnPlaceChange(
                        it
                    )
                )
            },
            label = { Text(text = "place") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )

        //ward
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.ward,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnWardChange(
                        it
                    )
                )
            },
            label = { Text(text = "ward") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )

        //telephone
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.telephone,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnTelephoneChange(
                        it
                    )
                )
            },
            label = { Text(text = "telephone") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )

        //email
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.email,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnEmailChange(
                        it
                    )
                )
            },
            label = { Text(text = "email") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )

        //password
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.password,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnPasswordChange(
                        it
                    )
                )
            },
            label = { Text(text = "password") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )

        //account
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = createAccount.account,
            onValueChange = {
                createAccount.executeCreateAccount(
                    events = CreateAccountListEvents.OnAccountChange(
                        it
                    )
                )
            },
            label = { Text(text = "account") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )
        //button Account Creation
        Spacer(modifier = Modifier.size(30.dp))
        Button(
            onClick = {
                createAccount.executeCreateAccount(events = CreateAccountListEvents.CreateAccount)
                if (createAccount.firstname.isBlank() || createAccount.lastname.isBlank() || createAccount.age.isBlank() ||
                    createAccount.place.isBlank() || createAccount.ward.isBlank() || createAccount.email.isBlank() ||
                    createAccount.password.isBlank() || createAccount.account.isBlank()
                ) {
                    Toast.makeText(
                        context,
                        "None of the fields should be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(0.80F).height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        ) {
            Text(text = "Create Account", color = Color.White, fontWeight = FontWeight.ExtraBold)
        }

        Spacer(modifier = Modifier.size(30.dp))

        if (createAccount.IsLoading == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        Spacer(modifier = Modifier.size(120.dp))

        var final: Bitmap? by remember { mutableStateOf(null) }

        var id = 1
        for (i in image.orEmpty()) {
            LaunchedEffect(key1 = true) {
                val imageLoader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(i.imageUrl)
                    .build()
                val drawable = imageLoader.execute(request = request).drawable
                final = (drawable as BitmapDrawable).toBitmapOrNull()
                createAccount.addProductToRoom(
                    product = LocalStoreProducts(
                        id = id,
                        productName = i.productName,
                        productImage = final!!,
                        productPrice = i.basePrice.toInt()
                    )
                )
                id += 1
            }
        }

    }

}