package com.example.ee_shopping.E

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ee_shopping.localReposity.LocalStoreProducts
import com.example.ee_shopping.userSerializeDataStore.ProductInformation

@Composable()
fun ProductScreen(nav:(ProductsUiEvents.NavigateToDetailProduct)->Unit) {

    //System Ui
//    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = !isSystemInDarkTheme()
//
//    DisposableEffect(systemUiController, useDarkIcons) {
//        // Update all of the system bar colors to be transparent, and use
//        // dark icons if we're in light theme
//        systemUiController.setSystemBarsColor(
//            color = Color.Transparent,
//            darkIcons = useDarkIcons
//        )
//        onDispose {}
//    }

    val context = LocalContext.current.applicationContext
    val product: ProductViewModel =
        viewModel(factory = ProductFactory(context = context as Application))

    LaunchedEffect(key1 = true) {
        product.oneTimeProducts.collect { event ->
            when (event) {
                is ProductsUiEvents.NavigateToDetailProduct -> {
                    nav(event)
                }
            }
        }
    }

    val myData by product.displayProducts.collectAsState(initial = emptyList())

    var name by remember { mutableStateOf("") }
    var filteredProduct: List<LocalStoreProducts>
    val newList: MutableList<LocalStoreProducts> = mutableListOf()
    val searchedName = name
    filteredProduct = if (searchedName.isEmpty()) {
        myData
    } else {
        for (cont in myData) {
            if (cont.productName.contains(name, ignoreCase = true)) {
                //  val a: Unit =  Text("$cont", fontWeight = FontWeight.ExtraBold)
                newList.add(cont)
            }
        }
        newList
    }
    val sortedProducts = filteredProduct.sortedBy { it.productName}

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(20.dp))
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

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(sortedProducts) { productItems ->
                SingleProductDesign(
                    productItems,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize()
                        .clickable {
                            product.executeProducts(ProductsListEvents.OnViewDetailProduct(
                                productItems))
                        }
                )

            }
        }
    }
}

@Composable
fun SingleProductDesign(product:LocalStoreProducts, modifier: Modifier=Modifier){
        Card(
            modifier = modifier,
           // modifier = Modifier.padding(5.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),

        ){
            Column(modifier = Modifier.fillMaxSize()) {
            Image(bitmap = product.productImage.asImageBitmap(), contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(400.dp,300.dp)
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = modifier.size(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = product.productName, color = Color.Black, fontWeight = FontWeight.ExtraBold)
                Text(text = "D${product.productPrice}",color = Color.Red, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}
