package com.example.ee_shopping.E

import android.app.Application
import android.content.ClipData
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ee_shopping.E.*
import com.example.ee_shopping.localReposity.CartProducts.CartProducts
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.remoteRepository.ErrorResult

@Composable
fun CartScreen(nav:(CartItemsUserEvents.NavigateToDetailProductScreen)->Unit){
    val context = LocalContext.current.applicationContext
    val cartItem: CartItemsViewModel =
        viewModel(factory = CartItemsFactory(context = context as Application))

    LaunchedEffect(key1 = true) {
        cartItem.oneTimeCartItem.collect { event ->
            when (event) {
                is CartItemsUserEvents.NavigateToDetailProductScreen -> {
                    nav(event)
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        cartItem.cartItemsChannel.collect { event ->
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(15.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cart-Items", fontWeight = FontWeight.ExtraBold)

        }
        Spacer(modifier = Modifier.size(10.dp))

        val displayCart by cartItem.displayCartItems.collectAsState(initial = emptyList())
        var name by remember { mutableStateOf("") }
        val filteredProduct: List<CartProducts>
        val newList: MutableList<CartProducts> = mutableListOf()
        val searchedName = name
        filteredProduct = if (searchedName.isEmpty()) {
            displayCart
        } else {
            for (cont in displayCart) {
                if (cont.productName.contains(name, ignoreCase = true)) {
                    //  val a: Unit =  Text("$cont", fontWeight = FontWeight.ExtraBold)
                    newList.add(cont)
                }
            }
            newList
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp)),
                label = {
                    Text(text = "Search product")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                },
                trailingIcon = {
                    if (name != "") {
                        IconButton(onClick = {
                            name = ""
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "")
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray,
                    leadingIconColor = Color.Blue,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White

                )
            )
        }
        Spacer(modifier = Modifier.size(10.dp))

        // val sortedProducts = filteredProduct.sortedBy { it.productName}
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredProduct) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.Transparent
                ) {
                Row (
                    modifier = Modifier.padding(5.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,

                        ){
                    Image(bitmap = it.productImage.asImageBitmap(),contentDescription = null,
                        modifier = Modifier
                            .size(150.dp,150.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                    Column {
                        Text(text = it.productName,color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif
                        )
                        Text(text = it.productPrice.toString(),color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif
                        )
                        Text(text = "Cartons-${it.productSize}",color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif
                            )

                    }
                    }

                }
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {

                    cartItem.executeCartItems(CartItemsListEvents.OnProceedToPayment)
                   // cartItem.name++
                },
                modifier = Modifier.fillMaxWidth(.80F).height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            ) {
                Text(text = "Proceed To Payment", color = Color.White, fontFamily = FontFamily.SansSerif)
            }
        }
        Spacer(modifier = Modifier.size(20.dp))


    }



}