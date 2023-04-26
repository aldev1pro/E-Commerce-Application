package com.example.ee_shopping.E

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ee_shopping.remoteRepository.ErrorResult
import kotlinx.coroutines.delay

@Composable
fun FinalAccountSetupScreen(nav:(FinalAccountUserEvents.NavigateToProductsScreen)->Unit) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current.applicationContext
        val finalAccount: FinalAccountSetupViewModel =
            viewModel(factory = FinalAccountSetupFactory(context = context as Application))

        LaunchedEffect(key1 = true) {
            finalAccount.finalAccountChannel.collect { event ->
                when (event) {
                    is ErrorResult.Success -> {
                        Log.d("Riles", event.data.toString().toList().toString())
                        Toast.makeText(context,
                            event.data.toString().toList().toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                    is ErrorResult.Error -> {
                        Toast.makeText(context, event.exception.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            finalAccount.oneTimeFinalAccount.collect { event ->
                when (event) {
                    is FinalAccountUserEvents.NavigateToProductsScreen -> {
                        Log.d("Riles1", event.route)
                        while (finalAccount.isLoading) {
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

        Text(text = "Your Account was successfully created please,",color = Color.Black, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = "Login To Your Account", color = Color.Black, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.size(50.dp))

        //Email TextField
        TextField(
            value = finalAccount.email,
            onValueChange = { finalAccount.executeFinalAccount(event = FinalAccountSetupListEvents.UserEmail(it)) },
            label = {Text(text = "Email")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)

        )

        //Password TextField
        TextField(
            value = finalAccount.password,
            onValueChange = { finalAccount.executeFinalAccount(event = FinalAccountSetupListEvents.UserPassword(it)) },
            label = {Text(text = "Password")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.size(40.dp))

        //Login Button
        var checkStatus by remember { mutableStateOf(false) }
        checkStatus = !(finalAccount.email.isBlank()||finalAccount.password.isBlank())
        Button(onClick = {
            finalAccount.executeFinalAccount(event = FinalAccountSetupListEvents.LogIn)
            if(finalAccount.email.isBlank() || finalAccount.password.isBlank()){
                Toast.makeText(context, "email or password can't be empty", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier.fillMaxWidth(0.80F).height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            enabled = checkStatus
            //border = BorderStroke(1.dp,Color.Black)
        ) {
            Text(text = "Login",color = Color.White, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(modifier = Modifier.size(20.dp))

        if (finalAccount.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


    }
}
